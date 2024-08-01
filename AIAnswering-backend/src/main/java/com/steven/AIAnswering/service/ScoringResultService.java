package com.steven.AIAnswering.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.steven.AIAnswering.model.dto.scoringResult.ScoringResultQueryRequest;
import com.steven.AIAnswering.model.entity.ScoringResult;
import com.steven.AIAnswering.model.vo.ScoringResultVO;

import javax.servlet.http.HttpServletRequest;

/**
 * scoringResult Service
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface ScoringResultService extends IService<ScoringResult> {

    /**
     * Verification Data
     *
     * @param scoringResult
     * @param add 
     */
    void validScoringResult(ScoringResult scoringResult, boolean add);

    /**
     * Get Query Wrapper
     *
     * @param scoringResultQueryRequest
     * @return
     */
    QueryWrapper<ScoringResult> getQueryWrapper(ScoringResultQueryRequest scoringResultQueryRequest);
    
    /**
     * Get scoringResult VO
     *
     * @param scoringResult
     * @param request
     * @return
     */
    ScoringResultVO getScoringResultVO(ScoringResult scoringResult, HttpServletRequest request);

    /**
     * Get scoringResult VO by Page
     *
     * @param scoringResultPage
     * @param request
     * @return
     */
    Page<ScoringResultVO> getScoringResultVOPage(Page<ScoringResult> scoringResultPage, HttpServletRequest request);
}
