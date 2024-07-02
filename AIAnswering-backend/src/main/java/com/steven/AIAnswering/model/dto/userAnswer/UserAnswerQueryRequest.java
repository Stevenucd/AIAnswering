package com.steven.AIAnswering.model.dto.userAnswer;

import com.steven.AIAnswering.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询userAnswer请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAnswerQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * app id
     */
    private Long appId;

    /**
     * Type of application (0-scoring category, 1-assessment category)
     */
    private Integer appType;

    /**
     * Scoring strategy (0-custom, 1-AI)
     */
    private Integer scoringStrategy;

    /**
     * user choices (JSON array)
     */
    private String choices;

    /**
     * result id
     */
    private Long resultId;

    /**
     * Result name, e.g. logistician
     */
    private String resultName;

    /**
     * Result description
     */
    private String resultDesc;

    /**
     * result picture
     */
    private String resultPicture;

    /**
     * score
     */
    private Integer resultScore;

    /**
     * user id
     */
    private Long userId;

    /**
     * id
     */
    private Long notId;

    /**
     * search text
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}