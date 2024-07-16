package com.mdsd.cloud.utils;

import java.nio.charset.StandardCharsets;

/**
 * @author WangYunwei [2024-07-14]
 */
public class ByteUtil {

    public static byte[] intToByte(int param) {

        byte[] result = new byte[4];
        result[0] = (byte) (param & 0xFF);
        result[1] = (byte) ((param >> 8) & 0xFF);
        result[2] = (byte) ((param >> 16) & 0xFF);
        result[3] = (byte) ((param >> 24) & 0xFF);
        return result;
    }

    public static byte[] stringToByte(String str){

        return str.getBytes(StandardCharsets.UTF_8);
    }
}
