package com.mdsd.cloud.response;

import java.io.Serializable;

/**
 * @author WangYunwei [2024-08-09]
 */
public class BusinessException extends RuntimeException implements Serializable {

    private String code = "0x0000";

    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }
}
