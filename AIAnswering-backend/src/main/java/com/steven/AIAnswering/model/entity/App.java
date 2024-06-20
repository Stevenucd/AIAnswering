package com.steven.AIAnswering.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * App
 * @TableName app
 */
@TableName(value ="app")
@Data
public class App implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * review time
     */
    private Date reviewTime;

    /**
     * user id
     */
    private Long userId;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * delete or not
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}