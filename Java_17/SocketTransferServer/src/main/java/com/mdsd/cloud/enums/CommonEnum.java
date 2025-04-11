package com.mdsd.cloud.enums;

import com.mdsd.cloud.response.BusinessException;

import java.util.Arrays;

/**
 * @author WangYunwei [2025-01-23]
 */
public enum CommonEnum {

    WEB_SOCKET_TYJW( "TYJW"),
    WEB_SOCKET_DJI( "DJI"),

    TCP_SOCKET_TYJW( null),

    UDP_SOCKET_DJI(null);

    private final String value;

    CommonEnum(String value) {
        this.value = value;
    }

    public static CommonEnum getEnumByDesc(String desc) {
        return Arrays.stream(CommonEnum.values()).filter(el -> desc.equals(el.value)).findFirst().orElseThrow(() -> new BusinessException("未知平台指令!"));
    }
}
