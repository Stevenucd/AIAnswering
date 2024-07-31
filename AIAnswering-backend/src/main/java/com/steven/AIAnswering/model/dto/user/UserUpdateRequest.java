package com.steven.AIAnswering.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User update requests
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
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

    private static final long serialVersionUID = 1L;
}