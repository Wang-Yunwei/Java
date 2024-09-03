package com.mdsd.cloud.enums;

import com.mdsd.cloud.response.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author WangYunwei [2024-09-03]
 */
public enum CommandEnum {

    机库_舱门_开启(0x00, 0x01, "140090"),
    机库_舱门_关闭(0x00, 0x02, "150000"),

    机库_推杆_开启(0x10, 0x01, "2f10002000"),
    机库_推杆_关闭(0x10, 0x02, "2e13362379"),

    机库_空调_开启(0x20, 0x01, "300000"),
    机库_空调_关闭(0x20, 0x02, "310000"),

    机库_无人机_开启(0x30, 0x01, "open"),
    机库_无人机_关闭(0x30, 0x02, "close");

    private int command;

    private int action;

    @Getter
    private String arg;

    CommandEnum(int command, int action, String arg) {
        this.command = command;
        this.action = action;
        this.arg = arg;
    }

    public static CommandEnum getCommandEnum(int command, int action) {

        return Arrays.stream(CommandEnum.values()).filter(el -> el.command == command && el.action == action).findFirst().orElseThrow(() -> {
            throw new BusinessException("未知指令动作!");
        });
    }
}
