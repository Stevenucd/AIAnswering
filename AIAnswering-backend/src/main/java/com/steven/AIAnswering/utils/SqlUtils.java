package com.steven.AIAnswering.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL Utils
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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
