package com.mdsd.cloud.utils;

import java.util.UUID;

/**
 * @author WangYunwei [2021-10-14]
 */
public class CommonUtils {

    /**
     * 获取随机 UUID
     */
    public static String GetRandomUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 空白字符串
     */
    public static String getNullCharacter(final String str, final int num) {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        if (num > 0) {
            for (int i = 0; i <= num; i++) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();

    }
}
