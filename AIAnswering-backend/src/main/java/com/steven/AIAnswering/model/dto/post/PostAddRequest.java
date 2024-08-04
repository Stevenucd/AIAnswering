package com.steven.AIAnswering.model.dto.post;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Creating Request
 *
 */
@Data
public class PostAddRequest implements Serializable {

    /**
     * Title
     */
    private String title;

    /**
     * Content
     */
    private String content;

    /**
     * Tags list
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}