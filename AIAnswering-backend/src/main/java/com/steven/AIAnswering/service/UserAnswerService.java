package com.steven.AIAnswering.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.steven.AIAnswering.model.dto.userAnswer.UserAnswerQueryRequest;
import com.steven.AIAnswering.model.entity.UserAnswer;
import com.steven.AIAnswering.model.vo.UserAnswerVO;

import javax.servlet.http.HttpServletRequest;

/**
 * userAnswer Service
 *
 */
public interface UserAnswerService extends IService<UserAnswer> {

    /**
     * Verification Data
     *
     * @param userAnswer
     * @param add 
     */
    void validUserAnswer(UserAnswer userAnswer, boolean add);

    /**
     * Get Query Wrapper
     *
     * @param userAnswerQueryRequest
     * @return
     */
    QueryWrapper<UserAnswer> getQueryWrapper(UserAnswerQueryRequest userAnswerQueryRequest);
    
    /**
     * Get userAnswer VO
     *
     * @param userAnswer
     * @param request
     * @return
     */
    UserAnswerVO getUserAnswerVO(UserAnswer userAnswer, HttpServletRequest request);

    /**
     * Get userAnswer VO by Page
     *
     * @param userAnswerPage
     * @param request
     * @return
     */
    Page<UserAnswerVO> getUserAnswerVOPage(Page<UserAnswer> userAnswerPage, HttpServletRequest request);
}
