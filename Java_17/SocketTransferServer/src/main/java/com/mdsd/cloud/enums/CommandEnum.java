package com.mdsd.cloud.enums;

/**
 * @author WangYunwei [2024-09-03]
 */
public enum CommandEnum {

    舱门_开启(0x72, 0x01,"140090"),
    舱门_关闭(0x72,0x02,"150000"),

    推杆_开启(0x72,0x11,"2f10002000"),
    推杆_关闭(0x72,0x12,"2e13362379"),

    空调_开启(0x72,0x21,"300000"),
    空调_关闭(0x72,0x22,"310000"),

    无人机_开启(0x72,0x31,"open"),
    无人机_关闭(0x72,0x32,"close");

    private int command;

    private int action;

    private String arg;

    CommandEnum(int command,int action, String arg) {
        this.command = command;
        this.action = action;
        this.arg = arg;
    }
}
