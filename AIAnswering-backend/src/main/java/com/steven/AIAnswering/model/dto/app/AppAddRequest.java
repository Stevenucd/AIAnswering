package com.steven.AIAnswering.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * Creating Application Requests
 *
 */
@Data
public class AppAddRequest implements Serializable {

    /**
     * application name
     */
    private String appName;

    /**
     * application description
     */
    private String appDesc;

    /**
     * application icon
     */
    private String appIcon;

    /**
     * Type of application (0-scoring category, 1-assessment category)
     */
    private Integer appType;

    /**
     * Scoring strategy (0-custom, 1-AI)
     */
    private Integer scoringStrategy;

    private static final long serialVersionUID = 1L;
}