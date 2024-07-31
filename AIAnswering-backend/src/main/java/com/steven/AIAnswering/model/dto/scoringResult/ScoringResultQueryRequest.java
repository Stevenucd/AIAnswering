package com.steven.AIAnswering.model.dto.scoringResult;

import com.steven.AIAnswering.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Query scoringResult request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoringResultQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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
     * Collection of Resulting Properties, JSON，e.g. [I,S,T,J]
     */
    private String resultProp;

    /**
     * Range of result scores, e.g. 80, means scores of 80 and above hit this result
     */
    private Integer resultScoreRange;

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

    /**
     * search text
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}