package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * AI generates question requests
 *
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {

    /**
     * App id
     */
    private Long appId;

    /**
     * Question number
     */
    int questionNumber = 10;

    /**
     * Option number
     */
    int optionNumber = 2;

    private static final long serialVersionUID = 1L;
}
