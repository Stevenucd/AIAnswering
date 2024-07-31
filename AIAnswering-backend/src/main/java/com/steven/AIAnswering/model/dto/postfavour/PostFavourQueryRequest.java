package com.steven.AIAnswering.model.dto.postfavour;

import com.steven.AIAnswering.model.dto.post.PostQueryRequest;
import com.steven.AIAnswering.common.PageRequest;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Post favourite query request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * Post query request
     */
    private PostQueryRequest postQueryRequest;

    /**
     * User id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}