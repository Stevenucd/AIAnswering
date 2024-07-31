package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * AI generates question requests
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
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
