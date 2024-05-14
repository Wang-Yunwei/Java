package com.example.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author WangYunwei [2022-03-16]
 */
@SpringBootTest
public class EncodePasswordTest {

    private StringEncryptor encryptor;

    public EncodePasswordTest(StringEncryptor encryptor){
        this.encryptor = encryptor;
    }

    @Value("${jasypt.encryptor.password}")
    private String str;

    @Test
    public void getEncrypt() {

        final String oraclePwd = this.encryptor.encrypt("Mdsd@2023");
        // 加密后的密码
        System.out.println("===> Encode password: " + oraclePwd);
        // 密钥
        System.out.println("===> Secret Key: " + this.str);
    }
}
