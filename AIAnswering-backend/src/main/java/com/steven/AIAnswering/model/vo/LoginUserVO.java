package com.steven.AIAnswering.model.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Logged in user VO (desensitised)
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 **/
@Data
public class LoginUserVO implements Serializable {

    /**
     * User id
     */
    private Long id;

    /**
     * Username
     */
    private String userName;

    /**
     * User avatar
     */
    private String userAvatar;

    /**
     * User profile
     */
    private String userProfile;

    /**
     * User role：user/admin/ban
     */
    private String userRole;

    /**
     * Create time
     */
    private Date createTime;

    /**
     * Update time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}