package com.steven.AIAnswering.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steven.AIAnswering.annotation.AuthCheck;
import com.steven.AIAnswering.common.BaseResponse;
import com.steven.AIAnswering.common.DeleteRequest;
import com.steven.AIAnswering.common.ErrorCode;
import com.steven.AIAnswering.common.ResultUtils;
import com.steven.AIAnswering.constant.UserConstant;
import com.steven.AIAnswering.exception.BusinessException;
import com.steven.AIAnswering.exception.ThrowUtils;
import com.steven.AIAnswering.manager.AiManager;
import com.steven.AIAnswering.model.dto.question.*;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.Question;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.model.enums.AppTypeEnum;
import com.steven.AIAnswering.model.vo.QuestionVO;
import com.steven.AIAnswering.service.AppService;
import com.steven.AIAnswering.service.QuestionService;
import com.steven.AIAnswering.service.UserService;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Question controller
 *
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    private AppService appService;

    @Resource
    private AiManager aiManager;


    // region CRUD

    /**
     * Create question
     *
     * @param questionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionAddRequest == null, ErrorCode.PARAMS_ERROR);
        // Convert entity class and DTOs here
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<QuestionContentDTO> questionContentDTO = questionAddRequest.getQuestionContent();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContentDTO));
        // Data verification
        questionService.validQuestion(question, true);
        // Fill defaults
        User loginUser = userService.getLoginUser(request);
        question.setUserId(loginUser.getId());
        // Write to database
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // Returns the newly written data id
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * Delete question
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // Determine exist or not
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // Can be deleted only by creator or an admin
        if (!oldQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Operational database
        boolean result = questionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * Update question (available to admin only)
     *
     * @param questionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Convert entity class and DTOs here
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<QuestionContentDTO> questionContentDTO = questionUpdateRequest.getQuestionContent();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContentDTO));
        // Data verification
        questionService.validQuestion(question, false);
        // Determine exist or not
        long id = questionUpdateRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // Operational database
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * Get question by id (Encapsulation)
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // Query database
        Question question = questionService.getById(id);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        // Get Encapsulation
        return ResultUtils.success(questionService.getQuestionVO(question, request));
    }

    /**
     * List questions by page (available to admin only)
     *
     * @param questionQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // Query database
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionPage);
    }

    /**
     * List question by page (Encapsulation)
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                               HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // Restrictions on crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // Query database
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        // Get Encapsulation
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * List my question by page
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(questionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // Supplementary query conditions to query only the data of the currently logged-in user
        User loginUser = userService.getLoginUser(request);
        questionQueryRequest.setUserId(loginUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // Restrictions on crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // Query database
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        // Get Encapsulation
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * Edit question (for users)
     *
     * @param questionEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest, HttpServletRequest request) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Convert entity class and DTOs here
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        List<QuestionContentDTO> questionContentDTO = questionEditRequest.getQuestionContent();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContentDTO));
        // Data verification
        questionService.validQuestion(question, false);
        User loginUser = userService.getLoginUser(request);
        // Determine exist or not
        long id = questionEditRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // Editable by creator or admin only
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Operational database
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion

    // region AI generate question function
    private static final String GENERATE_QUESTION_SYSTEM_MESSAGE = "You are a rigorous questioner and I will give you the following information：\n" +
            "```\n" +
            "Application Name,\n" +
            "【【【Application Description】】】,\n" +
            "application category,\n" +
            "Number of topics to be generated,\n" +
            "Number of options per question\n" +
            "```\n" +
            "\n" +
            "Based on the information above, please follow these steps to come up with a question:\n" +
            "1. Requirements: questions and options should be as short as possible, questions should not contain serial numbers, the number of options for each question should be based on what I have provided, and questions should not be repeated.\n" +
            "2. Strictly follow the following json format to output the questions and options\n" +
            "```\n" +
            "[{\"options\":[{\"value\":\"Option content\",\"key\":\"A\"},{\"value\":\"\",\"key\":\"B\"}],\"title\":\"Question Title\"}]\n" +
            "```\n" +
            "title is the title, options is the options, the key of each option is in alphabetical order (e.g. A, B, C, D) and so on, and value is the content of the option.\n" +
            "3. Check whether the title contains a serial number, and if so, remove it.\n" +
            "4. The returned list of topics must be in the form of a JSON array." +
            "5. Everything should be in English";

    /**
     * Generate user messages for question
     *
     * @param app
     * @param questionNumber
     * @param optionNumber
     * @return
     */
    private String getGenerateQuestionUserMessage(App app, int questionNumber, int optionNumber) {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(app.getAppName()).append("\n");
        userMessage.append(app.getAppDesc()).append("\n");
        userMessage.append(AppTypeEnum.getEnumByValue(app.getAppType()).getText() + "Type").append("\n");
        userMessage.append(questionNumber).append("\n");
        userMessage.append(optionNumber);
        return userMessage.toString();
    }

    @PostMapping("/ai_generate")
    public BaseResponse<List<QuestionContentDTO>> aiGenerateQuestion(
            @RequestBody AiGenerateQuestionRequest aiGenerateQuestionRequest) {
        ThrowUtils.throwIf(aiGenerateQuestionRequest == null, ErrorCode.PARAMS_ERROR);
        // Get Parameters
        Long appId = aiGenerateQuestionRequest.getAppId();
        int questionNumber = aiGenerateQuestionRequest.getQuestionNumber();
        int optionNumber = aiGenerateQuestionRequest.getOptionNumber();
        // Get app information
        App app = appService.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // Encapsulate Prompt
        String userMessage = getGenerateQuestionUserMessage(app, questionNumber, optionNumber);
        // AI generate
        String result = aiManager.doSyncRequest(GENERATE_QUESTION_SYSTEM_MESSAGE, userMessage, null);
        // Intercept the required JSON information
        int start = result.indexOf("[");
        int end = result.lastIndexOf("]");
        String json = result.substring(start, end + 1);
        List<QuestionContentDTO> questionContentDTOList = JSONUtil.toList(json, QuestionContentDTO.class);
        return ResultUtils.success(questionContentDTOList);
    }

    @GetMapping("/ai_generate/sse")
    public SseEmitter aiGenerateQuestionSSE(AiGenerateQuestionRequest aiGenerateQuestionRequest) {
        ThrowUtils.throwIf(aiGenerateQuestionRequest == null, ErrorCode.PARAMS_ERROR);
        // Get Parameters
        Long appId = aiGenerateQuestionRequest.getAppId();
        int questionNumber = aiGenerateQuestionRequest.getQuestionNumber();
        int optionNumber = aiGenerateQuestionRequest.getOptionNumber();
        // Get app information
        App app = appService.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // Encapsulate Prompt
        String userMessage = getGenerateQuestionUserMessage(app, questionNumber, optionNumber);
        // Create SSE connection object
        SseEmitter sseEmitter = new SseEmitter(0L);
        // AI generate, SSE streaming return
        Flowable<ModelData> modelDataFlowable = aiManager.doStreamRequest(GENERATE_QUESTION_SYSTEM_MESSAGE, userMessage, null);
        // Left bracket counter, in addition to the default value, when it becomes 0, it means that the left bracket is equal to the right bracket
        AtomicInteger counter = new AtomicInteger(0);
        // Splice question
        StringBuilder stringBuilder = new StringBuilder();
        modelDataFlowable
                .observeOn(Schedulers.io())
                .map(modelData -> modelData.getChoices().get(0).getDelta().getContent())
                .map(message -> message.replaceAll("[\\t\\n\\x0B\\f\\r]", ""))
                .filter(StrUtil::isNotBlank)
                .flatMap(message -> {
                    List<Character> characterList = new ArrayList<>();
                    for(char c : message.toCharArray()){
                        characterList.add(c);
                    }
                    return Flowable.fromIterable(characterList);
                })
                .doOnNext(c -> {
                    // If '{', counter++
                    if(c == '{'){
                        counter.addAndGet(1);
                    }
                    if(counter.get() > 0){
                        stringBuilder.append(c);
                    }
                    if(c == '}'){
                        counter.addAndGet(-1);
                        if(counter.get() == 0){
                            // The question can be spliced and returned to the front-end via SSE
                            sseEmitter.send(JSONUtil.toJsonStr(stringBuilder.toString()));
                            // reset stringBuilder
                            stringBuilder.setLength(0);
                        }
                    }
                })
                .doOnError((e) -> log.error("sse error", e))
                .doOnComplete(sseEmitter::complete)
                .subscribe();

        return sseEmitter;
    }

    // endregion
}
