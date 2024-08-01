package com.steven.AIAnswering.scoring;

import com.steven.AIAnswering.common.ErrorCode;
import com.steven.AIAnswering.exception.BusinessException;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.UserAnswer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Scoring Strategy Executor
 */
@Service
public class ScoringStrategyExecutor {

    // Strategy list
    @Resource
    private List<ScoringStrategy> scoringStrategyList;

    /**
     * Scoring
     *
     * @param choiceList
     * @param app
     * @return
     * @throws Exception
     */
    public UserAnswer doScore(List<String> choiceList, App app) throws Exception {
        Integer appType = app.getAppType();
        Integer appScoringStrategy = app.getScoringStrategy();
        if (appType == null || appScoringStrategy == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "The application was incorrectly configured and no matching strategy was found");
        }
        // Getting a strategy based on annotations
        for (ScoringStrategy strategy : scoringStrategyList) {
            if (strategy.getClass().isAnnotationPresent(ScoringStrategyConfig.class)) {
                ScoringStrategyConfig scoringStrategyConfig = strategy.getClass().getAnnotation(ScoringStrategyConfig.class);
                if (scoringStrategyConfig.appType() == appType && scoringStrategyConfig.scoringStrategy() == appScoringStrategy) {
                    return strategy.doScore(choiceList, app);
                }
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR, "The application was incorrectly configured and no matching strategy was found");
    }
}
