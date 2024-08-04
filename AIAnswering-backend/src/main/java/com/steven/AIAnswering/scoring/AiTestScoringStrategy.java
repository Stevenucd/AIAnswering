package com.steven.AIAnswering.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.steven.AIAnswering.manager.AiManager;
import com.steven.AIAnswering.model.dto.question.QuestionAnswerDTO;
import com.steven.AIAnswering.model.dto.question.QuestionContentDTO;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.Question;
import com.steven.AIAnswering.model.entity.UserAnswer;
import com.steven.AIAnswering.model.vo.QuestionVO;
import com.steven.AIAnswering.service.QuestionService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * AI Test Scoring Strategy
 *
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 1)
public class AiTestScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private AiManager aiManager;

    /**
     * AI Scoring System Message
     */
    private static final String AI_TEST_SCORING_SYSTEM_MESSAGE = "You are a rigorous judge of questions and I will give you the following information:\n" +
            "```\n" +
            "Application Name，\n" +
            "【【【Application Description】】】，\n" +
            "A list of questions and user responses: the format is [{\"title\": \"question\",\"answer\": \"user answer\"}]\n" +
            "```\n" +
            "\n" +
            "Based on the information above, please follow the steps below to evaluate the user:\n" +
            "1. Requirement: A clear evaluation outcome is required, including the name of the evaluation (as short as possible) and a description of the evaluation (as detailed as possible, more than 200 words).\n" +
            "2. Output the evaluation name and evaluation description strictly in the following json format\n" +
            "```\n" +
            "{\"resultName\": \"Name of evaluation\", \"resultDesc\": \"Evaluation description\"}\n" +
            "```\n" +
            "3. The return format must be a JSON object.\n"+
            "4. Everything in English";

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();
        // 1. Query the question according to id
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();

        // 2. Call AI for results
        // Encapsulate Prompt
        String userMessage = getAiTestScoringUserMessage(app, questionContent, choices);
        // AI generated
        String result = aiManager.doSyncStableRequest(AI_TEST_SCORING_SYSTEM_MESSAGE, userMessage);
        // Intercept the required JSON information
        int start = result.indexOf("{");
        int end = result.lastIndexOf("}");
        String json = result.substring(start, end + 1);

        // 3. Construct return values to populate the properties of the answer object
        UserAnswer userAnswer = JSONUtil.toBean(json, UserAnswer.class);
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        return userAnswer;
    }

    /**
     * AI Scoring User Message Encapsulation
     *
     * @param app
     * @param questionContentDTOList
     * @param choices
     * @return
     */
    private String getAiTestScoringUserMessage(App app, List<QuestionContentDTO> questionContentDTOList, List<String> choices) {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(app.getAppName()).append("\n");
        userMessage.append(app.getAppDesc()).append("\n");
        List<QuestionAnswerDTO> questionAnswerDTOList = new ArrayList<>();
        for (int i = 0; i < questionContentDTOList.size(); i++) {
            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
            questionAnswerDTO.setTitle(questionContentDTOList.get(i).getTitle());
            questionAnswerDTO.setUserAnswer(choices.get(i));
            questionAnswerDTOList.add(questionAnswerDTO);
        }
        userMessage.append(JSONUtil.toJsonStr(questionAnswerDTOList));
        return userMessage.toString();
    }

}
