package com.steven.AIAnswering.model.dto.postthumb;

import java.io.Serializable;
import lombok.Data;

/**
 * Post Likes Request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class PostThumbAddRequest implements Serializable {

    /**
     * Post id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}