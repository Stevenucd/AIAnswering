package com.steven.AIAnswering.model.dto.user;

import com.steven.AIAnswering.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User query request
 *
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