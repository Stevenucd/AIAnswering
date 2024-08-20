package com.steven.AIAnswering.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User create request
 *
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