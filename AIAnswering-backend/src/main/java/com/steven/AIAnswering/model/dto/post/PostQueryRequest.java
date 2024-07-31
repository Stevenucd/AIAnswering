package com.steven.AIAnswering.model.dto.post;

import com.steven.AIAnswering.common.PageRequest;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * Search text
     */
    private String searchText;

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

    /**
     * At least one tag
     */
    private List<String> orTags;

    /**
     * Create user id
     */
    private Long userId;

    /**
     * Favour user id
     */
    private Long favourUserId;

    private static final long serialVersionUID = 1L;
}