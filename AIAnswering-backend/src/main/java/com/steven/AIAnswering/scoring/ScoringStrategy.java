package com.steven.AIAnswering.scoring;

import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.UserAnswer;

import java.util.List;

/**
 * Scoring strategy
 *
* */
public interface ScoringStrategy {

    /**
     * do score
     *
     * @param choices
     * @param app
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}