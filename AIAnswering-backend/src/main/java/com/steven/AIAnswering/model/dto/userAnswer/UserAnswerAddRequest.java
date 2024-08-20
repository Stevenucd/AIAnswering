package com.steven.AIAnswering.model.dto.userAnswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Create userAnswer request
 *
 */
@Data
public class UserAnswerAddRequest implements Serializable {

    /**
     * id (user answer id, used to ensure the idempotency of the submitted answer)
     */
    private Long id;

    /**
     * app id
     */
    private Long appId;

    /**
     * user choices (JSON array)
     */
    private List<String> choices;

    private static final long serialVersionUID = 1L;
}