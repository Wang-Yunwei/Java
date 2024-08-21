package com.example.response.exception;

import java.io.Serializable;

public interface IResponse<T> extends Serializable {

    /**
     * 获取响应编码
     */
    String getCode();

    /**
     * 获取响应信息
     */
    String getMessage();

    /**
     * 获取响应消息体
     */
    T getBody();
}
