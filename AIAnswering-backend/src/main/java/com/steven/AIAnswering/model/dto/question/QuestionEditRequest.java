package com.steven.AIAnswering.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Edit question request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
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