package com.example.utils;

import org.springframework.util.StringUtils;

/**
 * @author WangYunwei [2021-09-18]
 */
public class HandleString {

    /**
     * 处理需要模糊查询的字符串
     * 例: "test" ——> "%test%"
     */
    public static String repString(final String str) {

        return StringUtils.isEmpty(str) ? null :
                "%" + str.replaceAll("\\\\", "\\\\\\\\").replaceAll("_", "\\\\_").replaceAll("%", "\\\\%") + "%";
    }
}
