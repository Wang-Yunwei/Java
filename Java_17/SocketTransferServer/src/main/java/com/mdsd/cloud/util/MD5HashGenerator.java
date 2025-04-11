package com.mdsd.cloud.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author WangYunwei [2024-07-30]
 */
public class MD5HashGenerator {

    public static String generateMD5(String str){

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte by:digest){
                String hexString = Integer.toHexString(by & 0xFF);
                if (hexString.length() == 1){
                    result.append('0');
                }
                result.append(hexString);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
