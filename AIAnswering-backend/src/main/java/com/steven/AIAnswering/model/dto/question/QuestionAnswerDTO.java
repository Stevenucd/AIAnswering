package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

/**
 * Question answer encapsulation (for AI scoring)
 */
@Data
public class QuestionAnswerDTO {

    /**
     * Title
     */
    private String title;

    /**
     * User answer
     */
    private String userAnswer;
}
