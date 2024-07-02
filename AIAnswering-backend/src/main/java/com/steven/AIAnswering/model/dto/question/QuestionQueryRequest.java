package com.steven.AIAnswering.model.dto.question;

import com.steven.AIAnswering.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询question请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * question content (json)
     */
    private String questionContent;

    /**
     * app id
     */
    private Long appId;

    /**
     * user id
     */
    private Long userId;

    /**
     * id
     */
    private Long notId;

    private static final long serialVersionUID = 1L;
}