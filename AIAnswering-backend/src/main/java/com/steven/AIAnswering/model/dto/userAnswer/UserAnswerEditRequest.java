package com.steven.AIAnswering.model.dto.userAnswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Edit userAnswer request
 *
 */
@Data
public class UserAnswerEditRequest implements Serializable {

    /**
     * id
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