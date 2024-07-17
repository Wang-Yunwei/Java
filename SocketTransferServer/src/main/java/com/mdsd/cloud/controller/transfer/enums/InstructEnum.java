package com.mdsd.cloud.controller.transfer.enums;

/**
 * @author WangYunwei [2024-07-16]
 */
public enum InstructEnum {

    注册((byte) 0x01, (byte) 0),
    心跳((byte) 0x02, (byte) 0),
    图片上传完成通知((byte) 0x09, (byte) 0),
    云盒开机通知((byte) 0x0A, (byte) 0x01),
    云盒关机通知((byte) 0x0A, (byte) 0xFF),
    信道质量((byte) 0x0C, (byte) 0),
    状态数据((byte) 0xA8, (byte) 0),
    遥测数据((byte) 0xA9, (byte) 0),

    云台转动_基于角度_回中((byte) 0xD1, (byte) 0x00),
    云台转动_基于角度_上((byte) 0xD1, (byte) 0x01),
    云台转动_基于角度_右上((byte) 0xD1, (byte) 0x02),
    云台转动_基于角度_右((byte) 0xD1, (byte) 0x03),
    云台转动_基于角度_右下((byte) 0xD1, (byte) 0x04),
    云台转动_基于角度_下((byte) 0xD1, (byte) 0x05),
    云台转动_基于角度_左下((byte) 0xD1, (byte) 0x06),
    云台转动_基于角度_左((byte) 0xD1, (byte) 0x07),
    云台转动_基于角度_左上((byte) 0xD1, (byte) 0x08),
    云台转动_基于角度_绝对值控制((byte) 0xD1, (byte) 0x09),
    变倍加((byte) 0xD1, (byte) 0x0A),
    变倍减((byte) 0xD1, (byte) 0x0B),
    变倍到指定倍数((byte) 0xD1, (byte) 0x0D),
    变倍复位((byte) 0xD1, (byte) 0x0F),
    航线规划((byte) 0xD1, (byte) 0x10),
    起飞((byte) 0xD1, (byte) 0x11),
    返航((byte) 0xD1, (byte) 0x12),
    取消返航((byte) 0xD1, (byte) 0x13),
    降落((byte) 0xD1, (byte) 0x14),
    取消降落((byte) 0xD1, (byte) 0x15),
    开始航线((byte) 0xD1, (byte) 0x17),
    暂停航线((byte) 0xD1, (byte) 0x18),
    恢复航线((byte) 0xD1, (byte) 0x19),
    实时激光测距((byte) 0xD1, (byte) 0x1A),
    手动激光测距((byte) 0xD1, (byte) 0x1B),
    对焦((byte) 0xD1, (byte) 0x1B),
    云台设置跟随模式((byte) 0xD1, (byte) 0x1C),
    云台设置姿态((byte) 0xD1, (byte) 0x1D),
    结束航线((byte) 0xD1, (byte) 0x20),
    设置返航高度((byte) 0xD1, (byte) 0x21),
    设置相机模式((byte) 0xD1, (byte) 0x22),
    拍照((byte) 0xD1, (byte) 0x23),
    开始录像((byte) 0xD1, (byte) 0x24),
    停止录像((byte) 0xD1, (byte) 0x25),
    切换视频流((byte) 0xD1, (byte) 0x26),
    切换摄像头((byte) 0xD1, (byte) 0x27),
    方向控制((byte) 0xD1, (byte) 0x28),
    强制降落((byte) 0xD1, (byte) 0x29),
    打开单点测温((byte) 0xD1, (byte) 0x2A),
    关闭单点测温((byte) 0xD1, (byte) 0x2B),
    打开区域测温((byte) 0xD1, (byte) 0x2C),
    关闭区域测温((byte) 0xD1, (byte) 0x2D),
    航线结束通知((byte) 0xD1, (byte) 0x2E),
    切换无人机控制权((byte) 0xD1, (byte) 0x30),
    设置返航点((byte) 0xD1, (byte) 0x31),
    紧急制动((byte) 0xD1, (byte) 0x32),
    水平避障设置((byte) 0xD1, (byte) 0x35),
    上避障设置((byte) 0xD1, (byte) 0x36),
    下避障设置((byte) 0xD1, (byte) 0x37),
    自动拍照((byte) 0xD1, (byte) 0x38),
    指点飞行((byte) 0xD1, (byte) 0x39),
    停止指点飞行((byte) 0xD1, (byte) 0x3A),
    无人机准备完成通知((byte) 0xD1, (byte) 0x3D),
    返航到指定机场((byte) 0xD1, (byte) 0x3E),
    格式化存储卡((byte) 0xD1, (byte) 0x40),
    打开或关闭照片回传((byte) 0xD1, (byte) 0x43),
    云台转动_基于速度((byte) 0xD1, (byte) 0xF4),
    云台转动_基于角度_停止((byte) 0xD1, (byte) 0xFE),
    变倍停止((byte) 0xD1, (byte) 0xFF),

    设置入网方式((byte) 0xD2, (byte) 0x01),
    设置视频码流((byte) 0xD2, (byte) 0x05),
    切换SIM卡((byte) 0xD2, (byte) 0x06),
    打开关闭AI识别((byte) 0xD2, (byte) 0x10),
    链路设置((byte) 0xD2, (byte) 0x1F),

    喊话模式切换((byte) 0xD3, (byte) 0x01),
    实时喊话((byte) 0xD3, (byte) 0x02),
    录音喊话((byte) 0xD3, (byte) 0x03),
    文字喊话((byte) 0xD3, (byte) 0x04),
    设置音量((byte) 0xD3, (byte) 0x05),
    设置循环播放((byte) 0xD3, (byte) 0x06),
    停止喊话((byte) 0xD3, (byte) 0xFF),

    机场任务完成通知((byte) 0xD5, (byte) 0xFE),

    开关灯((byte) 0xD6, (byte) 0x01),
    开关爆闪((byte) 0xD6, (byte) 0x02),
    功率限制开关((byte) 0xD6, (byte) 0x03),
    亮度设置((byte) 0xD6, (byte) 0x04),
    设置俯仰角((byte) 0xD6, (byte) 0x05),

    MOP数据透传((byte) 0xDC, (byte) 0),

    解锁开关((byte) 0xDD, (byte) 0x01),
    点火开关((byte) 0xDD, (byte) 0x02),
    喷火开关((byte) 0xDD, (byte) (byte) 0x03),
    燃料开关((byte) 0xDD, (byte) (byte) 0x04),
    其它开关((byte) 0xDD, (byte) (byte) 0x05),
    压力设置((byte) 0xDD, (byte) (byte) 0x06),
    档位设置((byte) 0xDD, (byte) (byte) 0x07),
    转动控制((byte) 0xDD, (byte) (byte) 0x08),
    设置喷火时间((byte) 0xDD, (byte) 0x09),
    避开喷火区开关((byte) 0xDD, (byte) 0xFE);

    private byte instruct;

    private byte action;

    InstructEnum(byte instruct, byte action) {

        this.instruct = instruct;
        this.action = action;
    }

    public byte getInstruct() {

        return instruct;
    }

    public byte getAction() {

        return action;
    }

    public static InstructEnum getEnum(final byte instruct) {

        if ( 0 != instruct) {

            for (final InstructEnum item : InstructEnum.values()) {
                if (instruct == item.getInstruct()) {
                    return item;
                }
            }
        }
        return null;
    }
}
