package com.steven.AIAnswering.scoring;

import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.UserAnswer;

import java.util.List;

/**
 * Scoring strategy
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
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