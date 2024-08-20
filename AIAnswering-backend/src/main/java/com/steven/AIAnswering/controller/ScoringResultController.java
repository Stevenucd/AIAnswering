package com.steven.AIAnswering.controller;

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
import com.steven.AIAnswering.model.dto.scoringResult.ScoringResultAddRequest;
import com.steven.AIAnswering.model.dto.scoringResult.ScoringResultEditRequest;
import com.steven.AIAnswering.model.dto.scoringResult.ScoringResultQueryRequest;
import com.steven.AIAnswering.model.dto.scoringResult.ScoringResultUpdateRequest;
import com.steven.AIAnswering.model.entity.ScoringResult;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.model.vo.ScoringResultVO;
import com.steven.AIAnswering.service.ScoringResultService;
import com.steven.AIAnswering.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Scoring results controller
 *
 */
@RestController
@RequestMapping("/scoringResult")
@Slf4j
public class ScoringResultController {

    @Resource
    private ScoringResultService scoringResultService;

    @Resource
    private UserService userService;

    // region CRUD

    /**
     * Create scoring results
     *
     * @param scoringResultAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addScoringResult(@RequestBody ScoringResultAddRequest scoringResultAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(scoringResultAddRequest == null, ErrorCode.PARAMS_ERROR);
        // Convert entity class and DTOs here
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultAddRequest, scoringResult);
        List<String> resultProp = scoringResultAddRequest.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        // Data verification
        scoringResultService.validScoringResult(scoringResult, true);
        // Fill defaults
        User loginUser = userService.getLoginUser(request);
        scoringResult.setUserId(loginUser.getId());
        // Write to database
        boolean result = scoringResultService.save(scoringResult);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // Returns the newly written data id
        long newScoringResultId = scoringResult.getId();
        return ResultUtils.success(newScoringResultId);
    }

    /**
     * Delete scoring results
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteScoringResult(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // Determine exist or not
        ScoringResult oldScoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(oldScoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // Can be deleted only by creator or an admin
        if (!oldScoringResult.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Operational database
        boolean result = scoringResultService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * Update scoring results (available to admin only)
     *
     * @param scoringResultUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateScoringResult(@RequestBody ScoringResultUpdateRequest scoringResultUpdateRequest) {
        if (scoringResultUpdateRequest == null || scoringResultUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Convert entity class and DTOs here
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultUpdateRequest, scoringResult);
        List<String> resultProp = scoringResultUpdateRequest.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        // Data verification
        scoringResultService.validScoringResult(scoringResult, false);
        // Determine exist or not
        long id = scoringResultUpdateRequest.getId();
        ScoringResult oldScoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(oldScoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // Operational database
        boolean result = scoringResultService.updateById(scoringResult);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * Get scoring result by id (Encapsulation)
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<ScoringResultVO> getScoringResultVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // Query database
        ScoringResult scoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(scoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // Get Encapsulation
        return ResultUtils.success(scoringResultService.getScoringResultVO(scoringResult, request));
    }

    /**
     * List scoring result by page (available to admin only)
     *
     * @param scoringResultQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<ScoringResult>> listScoringResultByPage(@RequestBody ScoringResultQueryRequest scoringResultQueryRequest) {
        long current = scoringResultQueryRequest.getCurrent();
        long size = scoringResultQueryRequest.getPageSize();
        // Query database
        Page<ScoringResult> scoringResultPage = scoringResultService.page(new Page<>(current, size),
                scoringResultService.getQueryWrapper(scoringResultQueryRequest));
        return ResultUtils.success(scoringResultPage);
    }

    /**
     * List scoring result by page（Encapsulation）
     *
     * @param scoringResultQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<ScoringResultVO>> listScoringResultVOByPage(@RequestBody ScoringResultQueryRequest scoringResultQueryRequest,
                                                                         HttpServletRequest request) {
        long current = scoringResultQueryRequest.getCurrent();
        long size = scoringResultQueryRequest.getPageSize();
        // Restrictions on crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // Query database
        Page<ScoringResult> scoringResultPage = scoringResultService.page(new Page<>(current, size),
                scoringResultService.getQueryWrapper(scoringResultQueryRequest));
        // Get Encapsulation
        return ResultUtils.success(scoringResultService.getScoringResultVOPage(scoringResultPage, request));
    }

    /**
     * List my scoring result by page
     *
     * @param scoringResultQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<ScoringResultVO>> listMyScoringResultVOByPage(@RequestBody ScoringResultQueryRequest scoringResultQueryRequest,
                                                                           HttpServletRequest request) {
        ThrowUtils.throwIf(scoringResultQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // Supplementary query conditions to query only the data of the currently logged-in user
        User loginUser = userService.getLoginUser(request);
        scoringResultQueryRequest.setUserId(loginUser.getId());
        long current = scoringResultQueryRequest.getCurrent();
        long size = scoringResultQueryRequest.getPageSize();
        // Restrictions on crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // Query database
        Page<ScoringResult> scoringResultPage = scoringResultService.page(new Page<>(current, size),
                scoringResultService.getQueryWrapper(scoringResultQueryRequest));
        // Get Encapsulation
        return ResultUtils.success(scoringResultService.getScoringResultVOPage(scoringResultPage, request));
    }

    /**
     * Edit scoring results (for users)
     *
     * @param scoringResultEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editScoringResult(@RequestBody ScoringResultEditRequest scoringResultEditRequest, HttpServletRequest request) {
        if (scoringResultEditRequest == null || scoringResultEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Convert entity class and DTOs here
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultEditRequest, scoringResult);
        List<String> resultProp = scoringResultEditRequest.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        // Data verification
        scoringResultService.validScoringResult(scoringResult, false);
        User loginUser = userService.getLoginUser(request);
        // Determine exist or not
        long id = scoringResultEditRequest.getId();
        ScoringResult oldScoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(oldScoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // Editable by creator or admin only
        if (!oldScoringResult.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Operational database
        boolean result = scoringResultService.updateById(scoringResult);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
