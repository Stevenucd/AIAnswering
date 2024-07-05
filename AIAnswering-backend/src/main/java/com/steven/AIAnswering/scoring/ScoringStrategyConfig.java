package com.steven.AIAnswering.scoring;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ScoringStrategyConfig {

    /**
     * Application type
     * @return
     */
    int appType();

    /**
     * Scoring strategy
     * @return
     */
    int scoringStrategy();
}