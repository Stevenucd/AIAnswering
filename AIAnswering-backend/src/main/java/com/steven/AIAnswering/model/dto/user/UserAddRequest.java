package com.steven.AIAnswering.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User create request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * Username
     */
    private String userName;

    /**
     * Account
     */
    private String userAccount;

    /**
     * User avatar
     */
    private String userAvatar;

    /**
     * User role: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}