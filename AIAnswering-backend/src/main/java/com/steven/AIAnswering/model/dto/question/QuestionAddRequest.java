package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Create question request
 *
 */
@Data
public class QuestionAddRequest implements Serializable {
    /**
     * question content (json)
     */
    private List<QuestionContentDTO> questionContent;

    /**
     * app id
     */
    private Long appId;

    private static final long serialVersionUID = 1L;
}