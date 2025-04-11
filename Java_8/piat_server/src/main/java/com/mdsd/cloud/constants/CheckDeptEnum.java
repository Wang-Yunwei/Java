package com.mdsd.cloud.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wcq
 * @version 1.0
 * @date 2021/9/16 14:06
 */
@Getter
@AllArgsConstructor
public enum CheckDeptEnum {

    CPC("CPC", "胸痛"),
    STROKE("STROKE", "卒中"),
    TRAUMA("TRAUMA", "创伤"),
    NEONATUS("NEONATUS", "新生儿"),
    MATERNAL("MATERNAL", "孕产妇"),
    ;

    @EnumValue
    private String value;
    private String text;
}
