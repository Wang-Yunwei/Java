package com.example;

/**
 * @author WangYunwei [2024-05-30]
 */
public class Base64Test {

    public static void main(String[] args){

        byte[] bytes = "java".getBytes();
        for ( byte b: bytes){
            // byte[] 中是 ACSll码,ASCll码共128个,byte类型的数据长度是 -128~127
            System.out.println(b +"——>"+ Integer.toBinaryString(b));
        }
        System.out.println(java.util.Base64.getEncoder().encodeToString(bytes));
    }
}
