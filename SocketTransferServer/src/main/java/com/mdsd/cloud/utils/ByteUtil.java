package com.mdsd.cloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author WangYunwei [2024-07-14]
 */
public class ByteUtil {

    private final static ObjectMapper om = new ObjectMapper();

    public static byte[] shortToByte(){


        byte[] result = new byte[2];
        result[0] = (byte) (0x6A77 >> 8); // 右移8位,得到高8位
        result[1] = (byte) (0x6A77 & 0xFF); // 与0xFF进行位与操作,得到低8位
        return result;
    }

    public static byte[] intToByte(int param) {
        // int 转成大端序
        byte[] result = new byte[4];
        result[0] = (byte) ((param >> 24) & 0xFF);
        result[1] = (byte) ((param >> 16) & 0xFF);
        result[2] = (byte) ((param >> 8) & 0xFF);
        result[3] = (byte) (param & 0xFF);
        return result;
    }

    public static byte[] stringToByte(String str){

        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String bytesToStringUTF8(byte[] bytes){

        return new String(bytes,StandardCharsets.UTF_8);
    }

    public static List<byte[]> splitByteArray(byte[] originalBytes, int chunkSize) {
        List<byte[]> subArrays = new ArrayList<>();

        for (int i = 0; i < originalBytes.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, originalBytes.length);
            byte[] subArray = new byte[end - i];
            System.arraycopy(originalBytes, i, subArray, 0, subArray.length);
            subArrays.add(subArray);
        }

        return subArrays;
    }
}
