package com.steven.AIAnswering.model.dto.postfavour;

import java.io.Serializable;
import lombok.Data;

/**
 * Post favourites / Cancel favourite request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class PostFavourAddRequest implements Serializable {

    /**
     * Post id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}