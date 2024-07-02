package com.steven.AIAnswering.model.dto.app;

import com.steven.AIAnswering.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询Application请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * application name
     */
    private String appName;

    /**
     * application description
     */
    private String appDesc;

    /**
     * application icon
     */
    private String appIcon;

    /**
     * Type of application (0-scoring category, 1-assessment category)
     */
    private Integer appType;

    /**
     * Scoring strategy (0-custom, 1-AI)
     */
    private Integer scoringStrategy;

    /**
     * Audit Status: 0-Pending, 1-Passed, 2-Rejected
     */
    private Integer reviewStatus;

    /**
     * review message
     */
    private String reviewMessage;

    /**
     * reviewer id
     */
    private Long reviewerId;

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