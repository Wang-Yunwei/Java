package com.mdsd.cloud.controller.transfer.enums;

/**
 * @author WangYunwei [2024-07-15]
 */
public enum ActionNumEnum {


    PLAN_A("","0x10");

    private String code;

    private String value;

    ActionNumEnum(String code,String value){
        this.code = code;
        this.value = value;
    }
}
