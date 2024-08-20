package com.steven.AIAnswering.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steven.AIAnswering.annotation.AuthCheck;
import com.steven.AIAnswering.common.*;
import com.steven.AIAnswering.constant.UserConstant;
import com.steven.AIAnswering.exception.BusinessException;
import com.steven.AIAnswering.exception.ThrowUtils;
import com.steven.AIAnswering.model.dto.app.AppAddRequest;
import com.steven.AIAnswering.model.dto.app.AppEditRequest;
import com.steven.AIAnswering.model.dto.app.AppQueryRequest;
import com.steven.AIAnswering.model.dto.app.AppUpdateRequest;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.model.enums.ReviewStatusEnum;
import com.steven.AIAnswering.model.vo.AppVO;
import com.steven.AIAnswering.service.AppService;
import com.steven.AIAnswering.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * App controller
 *
 */
@RestController
@RequestMapping("/app")
@Slf4j
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    private UserService userService;

    // region CRUD

    /**
     * Create app
     *
     * @param appAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addApp(@RequestBody AppAddRequest appAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(appAddRequest == null, ErrorCode.PARAMS_ERROR);
        // Convert entity class and DTOs here
        App app = new App();
        BeanUtils.copyProperties(appAddRequest, app);
        // Data verification
        appService.validApp(app, true);
        // Fill Defaults
        User loginUser = userService.getLoginUser(request);
        app.setUserId(loginUser.getId());
        app.setReviewStatus(ReviewStatusEnum.REVIEWING.getValue());
        // Write to database
        boolean result = appService.save(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // Returns the newly written data id
        long newAppId = app.getId();
        return ResultUtils.success(newAppId);
    }

    /**
     * Delete app
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteApp(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // Determine exist or not
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // Can be deleted only by creator or admin
        if (!oldApp.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Operational database
        boolean result = appService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * Update app (available only to admin)
     *
     * @param appUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateApp(@RequestBody AppUpdateRequest appUpdateRequest) {
        if (appUpdateRequest == null || appUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Convert entity class and DTOs here
        App app = new App();
        BeanUtils.copyProperties(appUpdateRequest, app);
        // Data verification
        appService.validApp(app, false);
        // Determine exist or not
        long id = appUpdateRequest.getId();
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // Operational database
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * Get app by id（Encapsulation）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<AppVO> getAppVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // Query database
        App app = appService.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // Get Encapsulation
        return ResultUtils.success(appService.getAppVO(app, request));
    }

    /**
     * Paging to get the list of apps (available only to admin)
     *
     * @param appQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<App>> listAppByPage(@RequestBody AppQueryRequest appQueryRequest) {
        long current = appQueryRequest.getCurrent();
        long size = appQueryRequest.getPageSize();
        // Query database
        Page<App> appPage = appService.page(new Page<>(current, size),
                appService.getQueryWrapper(appQueryRequest));
        return ResultUtils.success(appPage);
    }

    /**
     * Paging to get a list of apps (Encapsulation)
     *
     * @param appQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<AppVO>> listAppVOByPage(@RequestBody AppQueryRequest appQueryRequest,
                                                     HttpServletRequest request) {
        long current = appQueryRequest.getCurrent();
        long size = appQueryRequest.getPageSize();
        // Restrictions on crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // Only see reviewed apps
        appQueryRequest.setReviewStatus(ReviewStatusEnum.PASS.getValue());
        // Query database
        Page<App> appPage = appService.page(new Page<>(current, size),
                appService.getQueryWrapper(appQueryRequest));
        // Get Encapsulation
        return ResultUtils.success(appService.getAppVOPage(appPage, request));
    }

    /**
     * Paging to get a list of apps created by the currently logged-in user
     *
     * @param appQueryRequest
     * @param request
     * @return success
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<AppVO>> listMyAppVOByPage(@RequestBody AppQueryRequest appQueryRequest,
                                                       HttpServletRequest request) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // Supplementary query conditions to query only the data of the currently logged-in user
        User loginUser = userService.getLoginUser(request);
        appQueryRequest.setUserId(loginUser.getId());
        long current = appQueryRequest.getCurrent();
        long size = appQueryRequest.getPageSize();
        // Restrictions on crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // Query database
        Page<App> appPage = appService.page(new Page<>(current, size),
                appService.getQueryWrapper(appQueryRequest));
        // Get Encapsulation
        return ResultUtils.success(appService.getAppVOPage(appPage, request));
    }

    /**
     * Edit app (for users)
     *
     * @param appEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editApp(@RequestBody AppEditRequest appEditRequest, HttpServletRequest request) {
        if (appEditRequest == null || appEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Convert entity class and DTOs here
        App app = new App();
        BeanUtils.copyProperties(appEditRequest, app);
        // Data verification
        appService.validApp(app, false);
        User loginUser = userService.getLoginUser(request);
        // Determine exist or not
        long id = appEditRequest.getId();
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // Editable by creator or admin only
        if (!oldApp.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Reset audit status
        app.setReviewStatus(ReviewStatusEnum.REVIEWING.getValue());
        // Operational database
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion

    /**
     * App Review
     *
     * @param reviewRequest
     * @param request
     * @return
     */
    @PostMapping("/review")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> doAppReview(@RequestBody ReviewRequest reviewRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(reviewRequest == null, ErrorCode.PARAMS_ERROR);
        Long id = reviewRequest.getId();
        Integer reviewStatus = reviewRequest.getReviewStatus();
        // Verification
        ReviewStatusEnum reviewStatusEnum = ReviewStatusEnum.getEnumByValue(reviewStatus);
        if (id == null || reviewStatusEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Determine exist or not
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // Already in this state
        if (oldApp.getReviewStatus().equals(reviewStatus)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请勿重复审核");
        }
        // Update audit status
        User loginUser = userService.getLoginUser(request);
        App app = new App();
        app.setId(id);
        app.setReviewStatus(reviewStatus);
        app.setReviewMessage(reviewRequest.getReviewMessage());
        app.setReviewerId(loginUser.getId());
        app.setReviewTime(new Date());
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
}
