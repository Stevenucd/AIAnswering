package com.steven.AIAnswering.model.dto.scoringResult;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Create scoringResult request
 *
 */
@Data
public class ScoringResultAddRequest implements Serializable {

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
    private List<String> resultProp;

    /**
     * Range of result scores, e.g. 80, means scores of 80 and above hit this result
     */
    private Integer resultScoreRange;

    /**
     * app id
     */
    private Long appId;

    private static final long serialVersionUID = 1L;
}