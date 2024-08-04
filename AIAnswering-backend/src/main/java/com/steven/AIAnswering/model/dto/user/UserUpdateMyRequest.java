package com.steven.AIAnswering.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * User update personal information request
 *
 */
@Data
public class UserUpdateMyRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}