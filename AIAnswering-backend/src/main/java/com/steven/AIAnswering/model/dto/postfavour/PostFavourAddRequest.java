package com.steven.AIAnswering.model.dto.postfavour;

import java.io.Serializable;
import lombok.Data;

/**
 * Post favourites / Cancel favourite request
 *
 */
@Data
public class PostFavourAddRequest implements Serializable {

    /**
     * Post id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}