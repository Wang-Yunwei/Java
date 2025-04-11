package com.mdsd.cloud.enums;

import com.mdsd.cloud.response.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author WangYunwei [2024-09-03]
 */
@Getter
public enum HangarEnum {

    机库_充电操作_检查(0x03, 0x05, "Check"),
    机库_充电操作_充电(0x03, 0x04, "Charge"),
    机库_充电操作_待机(0x03, 0x03, "Standby"),
    机库_充电操作_关机(0x03, 0x02, "DroneOff"),
    机库_充电操作_开机(0x03, 0x01, "TakeOff"),

    机库_空调_关(0x02, 0x02, "310000"),
    机库_空调_开(0x02, 0x01, "300000"),

    机库_推杆_关(0x01, 0x02, "2e13362379"),
    机库_推杆_开(0x01, 0x01, "2f10002000"),

    机库_舱门_关(0x00, 0x02, "150000"),
    机库_舱门_开(0x00, 0x01, "140090"),
    机库_状态(0x00, 0x00, null);

    private final int command;

    private final int action;

    private final String arg;

    HangarEnum(int command, int action, String arg) {
        this.command = command;
        this.action = action;
        this.arg = arg;
    }

    public static HangarEnum getCommandEnum(int command, int action) {

        return Arrays.stream(HangarEnum.values()).filter(el -> el.command == command && el.action == action).findFirst().orElseThrow(() -> new BusinessException("未知指令动作!"));
    }
}
