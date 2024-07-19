package com.steven.AIAnswering.common;

import com.steven.AIAnswering.constant.CommonConstant;
import lombok.Data;

/**
 * Page break request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class PageRequest {

    /**
     * Current page
     */
    private int current = 1;

    /**
     * Page size
     */
    private int pageSize = 10;

    /**
     * Sort field
     */
    private String sortField;

    /**
     * Sort order (default ascending)
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
