package com.steven.AIAnswering.model.dto.userAnswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Update userAnswer request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class UserAnswerUpdateRequest implements Serializable {

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