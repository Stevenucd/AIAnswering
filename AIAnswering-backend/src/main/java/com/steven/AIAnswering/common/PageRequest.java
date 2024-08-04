package com.steven.AIAnswering.common;

import com.steven.AIAnswering.constant.CommonConstant;
import lombok.Data;

/**
 * Page break request
 *
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
