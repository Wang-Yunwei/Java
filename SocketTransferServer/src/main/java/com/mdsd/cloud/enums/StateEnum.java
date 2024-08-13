package com.mdsd.cloud.enums;

import lombok.Getter;

/**
 * @author WangYunwei [2024-08-09]
 */
@Getter
public enum StateEnum {


    STATE_0(0, "成功"),
    STATE_102(-102, "云盒不在线!"),
    STATE_201(-201, "您尚未登录,重新登录后再试!");

    private int key;

    private String description;

    StateEnum(int key, String description) {
        this.key = key;
        this.description = description;
    }

    public static StateEnum getStateEnum(int key) {

        for (StateEnum stateEnum : StateEnum.values()) {
            if (key == stateEnum.key) {
                return stateEnum;
            }
        }
        return null;
    }

}
