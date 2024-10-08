package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Edit question request
 *
 */
@Data
public class QuestionEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * question content (json)
     */
    private List<QuestionContentDTO> questionContent;

    private static final long serialVersionUID = 1L;
}