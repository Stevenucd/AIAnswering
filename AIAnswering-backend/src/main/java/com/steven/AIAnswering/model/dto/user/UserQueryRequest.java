package com.steven.AIAnswering.model.dto.user;

import com.steven.AIAnswering.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User query request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * Union id
     */
    private String unionId;

    /**
     * mp open id
     */
    private String mpOpenId;

    /**
     * Username
     */
    private String userName;

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