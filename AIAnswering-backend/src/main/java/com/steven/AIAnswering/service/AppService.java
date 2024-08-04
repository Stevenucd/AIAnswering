package com.steven.AIAnswering.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.steven.AIAnswering.model.dto.app.AppQueryRequest;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.vo.AppVO;

import javax.servlet.http.HttpServletRequest;

/**
 * Application Service
 *
 */
public interface AppService extends IService<App> {

    /**
     * Verification Data
     *
     * @param app
     * @param add 
     */
    void validApp(App app, boolean add);

    /**
     * Get Query Wrapper
     *
     * @param appQueryRequest
     * @return
     */
    QueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest);
    
    /**
     * Get Application VO
     *
     * @param app
     * @param request
     * @return
     */
    AppVO getAppVO(App app, HttpServletRequest request);

    /**
     * Get Application VO by Page
     *
     * @param appPage
     * @param request
     * @return
     */
    Page<AppVO> getAppVOPage(Page<App> appPage, HttpServletRequest request);
}
