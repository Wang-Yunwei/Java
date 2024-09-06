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

    机库_推杆_开启(0x01, 0x01, "2f10002000"),
    机库_推杆_关闭(0x01, 0x02, "2e13362379"),

    机库_空调_开启(0x02, 0x01, "300000"),
    机库_空调_关闭(0x02, 0x02, "310000"),

    机库_无人机_开启(0x03, 0x01, "open"),
    机库_无人机_关闭(0x03, 0x02, "close"),

    机库_充电操作_开机(0x04, 0x01, "TakeOff"),
    机库_充电操作_关机(0x04, 0x02, "DroneOff"),
    机库_充电操作_待机(0x04, 0x03, "Standby"),
    机库_充电操作_充电(0x04, 0x04, "Charge"),
    机库_充电操作_检查(0x04, 0x05, "Check");

    private final int command;

    private final int action;

    @Getter
    private final String arg;

    CommandEnum(int command, int action, String arg) {
        this.command = command;
        this.action = action;
        this.arg = arg;
    }

    public static CommandEnum getCommandEnum(int command, int action) {

        return Arrays.stream(CommandEnum.values()).filter(el -> el.command == command && el.action == action).findFirst().orElseThrow(() -> new BusinessException("未知指令动作!"));
    }
}
