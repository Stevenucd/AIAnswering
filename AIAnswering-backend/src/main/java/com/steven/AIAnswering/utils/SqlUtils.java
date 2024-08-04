package com.steven.AIAnswering.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL Utils
 *
 */
public class SqlUtils {

    /**
     * Verify that sorted fields are legal (prevent SQL injection)
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
