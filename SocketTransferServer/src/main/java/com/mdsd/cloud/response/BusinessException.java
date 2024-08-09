package com.mdsd.cloud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author WangYunwei [2024-08-09]
 */
public class BusinessException extends RuntimeException implements Serializable {

    private String code;

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
