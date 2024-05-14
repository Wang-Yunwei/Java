/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2021
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.example.test;

/**
 * @author WangYunwei [2021-07-24]
 */
public class RandomCharacterTest {

    final static char[] CHARS_A = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};

    final static char[] CHARS_B = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'};

    /**
     * 获取随机字符
     */
    public static String getChar(int charLength) {

        final StringBuilder stringBuilder = new StringBuilder();
        while (charLength > 0) {
            charLength--;
            stringBuilder.append(CHARS_A[(int) (Math.random() * 62)]);
        }
        return stringBuilder.toString();
    }

    /**
     * 测试获取随机字符
     */
    public static void main(String[] args){

        System.out.println(getChar(18));
    }

}
