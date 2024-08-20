package com.steven.AIAnswering.model.dto.post;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Editing Request
 *
 */
@Data
public class PostEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * Title
     */
    private String title;

    /**
     * content
     */
    private String content;

    /**
     * Tags list
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}