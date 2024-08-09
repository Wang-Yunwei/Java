package com.mdsd.cloud.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author WangYunwei [2024-07-11]
 */
@Getter
@Setter
@ToString
public class ResponseTy<T> implements Serializable {

    /**
     * 0为成功,其它为失败
     */
    private int state;

    /**
     * 操作描述,当state不为0时参见此值
     */
    private String message;

    /**
     * 记录总数,在列表接口返回时有此值
     */
    private int totalNum;

    /**
     * 实际返回数,可能为 list 或 map
     */
    private T content;
}
