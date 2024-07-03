package com.steven.AIAnswering.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Review status
 */
@Data
public class ReviewRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * status：0-pending, 1-passed, 2-rejected
     */
    private Integer reviewStatus;

    /**
     * review message
     */
    private String reviewMessage;


    private static final long serialVersionUID = 1L;
}