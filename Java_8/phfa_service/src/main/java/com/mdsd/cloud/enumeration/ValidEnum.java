package com.mdsd.cloud.enumeration;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wcq
 * @date 2021/9/16 14:06
 */
@Getter
@AllArgsConstructor
public enum ValidEnum {

    INVALID("0", "无效"),
    VALID("1", "有效"),
    ;

    @EnumValue
    private String value;

    private String text;
}
