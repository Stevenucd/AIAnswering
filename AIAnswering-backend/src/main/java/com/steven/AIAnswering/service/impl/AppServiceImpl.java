package com.steven.AIAnswering.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.AIAnswering.common.ErrorCode;
import com.steven.AIAnswering.constant.CommonConstant;
import com.steven.AIAnswering.exception.ThrowUtils;
import com.steven.AIAnswering.mapper.AppMapper;
import com.steven.AIAnswering.model.dto.app.AppQueryRequest;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.model.enums.AppScoringStrategyEnum;
import com.steven.AIAnswering.model.enums.AppTypeEnum;
import com.steven.AIAnswering.model.enums.ReviewStatusEnum;
import com.steven.AIAnswering.model.vo.AppVO;
import com.steven.AIAnswering.model.vo.UserVO;
import com.steven.AIAnswering.service.AppService;
import com.steven.AIAnswering.service.UserService;
import com.steven.AIAnswering.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * App Service Implementation
 *
 */
@Service
@Slf4j
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Resource
    private UserService userService;

    /**
     * Data verification
     *
     * @param app
     * @param add
     */
    @Override
    public void validApp(App app, boolean add) {
        ThrowUtils.throwIf(app == null, ErrorCode.PARAMS_ERROR);
        // Taking values from an object
        String appName = app.getAppName();
        String appDesc = app.getAppDesc();
        Integer appType = app.getAppType();
        Integer scoringStrategy = app.getScoringStrategy();
        Integer reviewStatus = app.getReviewStatus();

        // Parameters cannot be empty when creating data
        if (add) {
            ThrowUtils.throwIf(StringUtils.isBlank(appName), ErrorCode.PARAMS_ERROR, "Application name cannot be empty");
            ThrowUtils.throwIf(StringUtils.isBlank(appDesc), ErrorCode.PARAMS_ERROR, "Application description cannot be empty");
            AppTypeEnum appTypeEnum = AppTypeEnum.getEnumByValue(appType);
            ThrowUtils.throwIf(appTypeEnum == null, ErrorCode.PARAMS_ERROR, "Application category illegal");
            AppScoringStrategyEnum scoringStrategyEnum = AppScoringStrategyEnum.getEnumByValue(scoringStrategy);
            ThrowUtils.throwIf(scoringStrategyEnum == null, ErrorCode.PARAMS_ERROR, "Application scoring strategies illegal");
        }
        if (StringUtils.isNotBlank(appName)) {
            ThrowUtils.throwIf(appName.length() > 80, ErrorCode.PARAMS_ERROR, "Application name should be less than 80");
        }
        if (reviewStatus != null) {
            ReviewStatusEnum reviewStatusEnum = ReviewStatusEnum.getEnumByValue(reviewStatus);
            ThrowUtils.throwIf(reviewStatusEnum == null, ErrorCode.PARAMS_ERROR, "Audit Status Illegal");
        }
    }

    /**
     * Get Query Wrapper
     *
     * @param appQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        if (appQueryRequest == null) {
            return queryWrapper;
        }
        // Get values from an object
        Long id = appQueryRequest.getId();
        String appName = appQueryRequest.getAppName();
        String appDesc = appQueryRequest.getAppDesc();
        String appIcon = appQueryRequest.getAppIcon();
        Integer appType = appQueryRequest.getAppType();
        Integer scoringStrategy = appQueryRequest.getScoringStrategy();
        Integer reviewStatus = appQueryRequest.getReviewStatus();
        String reviewMessage = appQueryRequest.getReviewMessage();
        Long reviewerId = appQueryRequest.getReviewerId();
        Long userId = appQueryRequest.getUserId();
        Long notId = appQueryRequest.getNotId();
        String searchText = appQueryRequest.getSearchText();
        String sortField = appQueryRequest.getSortField();
        String sortOrder = appQueryRequest.getSortOrder();

        // Supplementary required queries
        // Search from multiple fields
        if (StringUtils.isNotBlank(searchText)) {
            // Need to splice query conditions
            queryWrapper.and(qw -> qw.like("appName", searchText).or().like("appDesc", searchText));
        }
        // Fuzzy queries
        queryWrapper.like(StringUtils.isNotBlank(appName), "appName", appName);
        queryWrapper.like(StringUtils.isNotBlank(appDesc), "appDesc", appDesc);
        queryWrapper.like(StringUtils.isNotBlank(reviewMessage), "reviewMessage", reviewMessage);
        // Precise search
        queryWrapper.eq(StringUtils.isNotBlank(appIcon), "appIcon", appIcon);
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(appType), "appType", appType);
        queryWrapper.eq(ObjectUtils.isNotEmpty(scoringStrategy), "scoringStrategy", scoringStrategy);
        queryWrapper.eq(ObjectUtils.isNotEmpty(reviewStatus), "reviewStatus", reviewStatus);
        queryWrapper.eq(ObjectUtils.isNotEmpty(reviewerId), "reviewerId", reviewerId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        // Sorting rules
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * Get App VO
     *
     * @param app
     * @param request
     * @return
     */
    @Override
    public AppVO getAppVO(App app, HttpServletRequest request) {
        // Object to VO
        AppVO appVO = AppVO.objToVo(app);

        // region
        // 1. Linked query user information
        Long userId = app.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        appVO.setUser(userVO);
        // endregion
        return appVO;
    }

    /**
     * Get App VO Page
     *
     * @param appPage
     * @param request
     * @return
     */
    @Override
    public Page<AppVO> getAppVOPage(Page<App> appPage, HttpServletRequest request) {
        List<App> appList = appPage.getRecords();
        Page<AppVO> appVOPage = new Page<>(appPage.getCurrent(), appPage.getSize(), appPage.getTotal());
        if (CollUtil.isEmpty(appList)) {
            return appVOPage;
        }
        List<AppVO> appVOList = appList.stream().map(app -> {
            return AppVO.objToVo(app);
        }).collect(Collectors.toList());

        // region
        // 1. Linked query user information
        Set<Long> userIdSet = appList.stream().map(App::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // Filling information
        appVOList.forEach(appVO -> {
            Long userId = appVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            appVO.setUser(userService.getUserVO(user));
        });
        // endregion

        appVOPage.setRecords(appVOList);
        return appVOPage;
    }

}
