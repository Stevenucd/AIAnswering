package com.steven.AIAnswering.model.dto.post;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Update Request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class PostUpdateRequest implements Serializable {

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