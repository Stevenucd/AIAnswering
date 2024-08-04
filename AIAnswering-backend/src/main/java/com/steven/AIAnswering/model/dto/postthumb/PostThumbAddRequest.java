package com.steven.AIAnswering.model.dto.postthumb;

import java.io.Serializable;
import lombok.Data;

/**
 * Post Likes Request
 *
 */
@Data
public class PostThumbAddRequest implements Serializable {

    /**
     * Post id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}