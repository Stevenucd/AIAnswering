package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Create question request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
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