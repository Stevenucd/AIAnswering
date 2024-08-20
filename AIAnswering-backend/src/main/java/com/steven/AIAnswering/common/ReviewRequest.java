package com.steven.AIAnswering.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Audit requests
 */
@Data
public class ReviewRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * Status：0-Pending, 1-Pass, 2-Reject
     */
    private Integer reviewStatus;

    /**
     * Audit information
     */
    private String reviewMessage;


    private static final long serialVersionUID = 1L;
}