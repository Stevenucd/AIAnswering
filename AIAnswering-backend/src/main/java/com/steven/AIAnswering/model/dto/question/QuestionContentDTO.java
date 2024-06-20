package com.steven.AIAnswering.model.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionContentDTO {

    /**
     * question title
     */
    private String title;

    /**
     * list of topic options
     */
    private List<Option> options;

    /**
     * title options
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private String result;
        private int score;
        private String value;
        private String key;
    }
}


