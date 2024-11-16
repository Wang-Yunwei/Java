package com.mdsd.cloud.enums;

import com.mdsd.cloud.response.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author WangYunwei [2024-07-16]
 */
@Getter
public enum TyjwEnum {

    请求帧头(0x7479, 0, null),
    回复帧头(0x6A77, 0, null),
    注册(0x01, 0, null),
    心跳(0x02, 0, null),
    图片上传完成通知(0x09, 0, null),
    云盒开关机通知(0x0A, 0, null),
    信道质量(0x0C, 0, null),
    状态数据(0xA8, 0, null),
    遥测数据(0xA9, 0, null),

    云台转动_基于角度_回中(0xD1, 0x00, "转动角度-byte;"),
    云台转动_基于角度_上(0xD1, 0x01, "转动角度-byte;"),
    云台转动_基于角度_右上(0xD1, 0x02, "转动角度-byte;"),
    云台转动_基于角度_右(0xD1, 0x03, "转动角度-byte;"),
    云台转动_基于角度_右下(0xD1, 0x04, "转动角度-byte;"),
    云台转动_基于角度_下(0xD1, 0x05, "转动角度-byte;"),
    云台转动_基于角度_左下(0xD1, 0x06, "转动角度-byte;"),
    云台转动_基于角度_左(0xD1, 0x07, "转动角度-byte;"),
    云台转动_基于角度_左上(0xD1, 0x08, "转动角度-byte;"),
    云台转动_基于角度_绝对值控制(0xD1, 0x09, "俯仰-float;平移-float;横滚-float;"),
    变倍加(0xD1, 0x0A, null),
    变倍减(0xD1, 0x0B, null),
    变倍到指定倍数(0xD1, 0x0D, "变倍数值-byte;"),
    变倍复位(0xD1, 0x0F, null),
    航线规划(0xD1, 0x10, "航线数据-bytes;"),
    起飞(0xD1, 0x11, "飞起高度-float;"),
    返航(0xD1, 0x12, null),
    取消返航(0xD1, 0x13, null),
    降落(0xD1, 0x14, null),
    取消降落(0xD1, 0x15, null),
    开始航线(0xD1, 0x17, null),
    暂停航线(0xD1, 0x18, null),
    恢复航线(0xD1, 0x19, null),
    实时激光测距(0xD1, 0x1A, "数据-byte;"),
    手动激光测距(0xD1, 0x1B, "X点坐标-float;Y点坐标-float;"),
    对焦(0xD1, 0x1B, "X点坐标-float;Y点坐标-float;"),
    云台设置跟随模式(0xD1, 0x1C, "数据-byte;"),
    云台设置姿态(0xD1, 0x1D, "数据-byte;"),
    结束航线(0xD1, 0x20, null),
    设置返航高度(0xD1, 0x21, "数据-byte;"),
    设置相机模式(0xD1, 0x22, "动作参数-byte;"),
    拍照(0xD1, 0x23, null),
    开始录像(0xD1, 0x24, null),
    停止录像(0xD1, 0x25, null),
    切换视频源(0xD1, 0x26, "动作参数-byte;"),
    切换摄像头(0xD1, 0x27, "动作参数-byte;"),
    方向控制(0xD1, 0x28, "前后速度-float;左右速度-float;上下速度-float;偏航角-float;执行时间-short;"),
    强制降落(0xD1, 0x29, null),
    打开单点测温(0xD1, 0x2A, "X点坐标-float;Y点坐标-float;"),
    关闭单点测温(0xD1, 0x2B, null),
    打开区域测温(0xD1, 0x2C, "X1点坐标-float;Y1点坐标-float;X2点坐标-float;Y2点坐标-float;"),
    关闭区域测温(0xD1, 0x2D, null),
    航线结束通知(0xD1, 0x2E, null),
    切换无人机控制权(0xD1, 0x30, "数据-byte;"),
    设置返航点(0xD1, 0x31, "经度-double;纬度-double;"),
    紧急制动(0xD1, 0x32, "数据-byte;"),
    水平避障设置(0xD1, 0x35, "数据-byte;"),
    上避障设置(0xD1, 0x36, "数据-byte;"),
    下避障设置(0xD1, 0x37, "数据-byte;"),
    自动拍照(0xD1, 0x38, "数据-byte;"),
    指点飞行(0xD1, 0x39, "经度-double;纬度-double;高度-float;速度-float;飞行模式-byte;"),
    停止指点飞行(0xD1, 0x3A, null),
    无人机准备完成通知(0xD1, 0x3D, null),
    返航到指定机场(0xD1, 0x3E, "返航高度-short;机库经度-double;机库纬度-double;备降点经度-double;备降点纬度-double;机库ID-bytes;"),
    格式化存储卡(0xD1, 0x40, null),
    打开或关闭照片回传(0xD1, 0x43, "数据-byte;"),
    云台转动_基于速度(0xD1, 0xF4, "俯仰-float;平移-float;横滚-float;执行时间-short;"),
    云台转动_基于角度_停止(0xD1, 0xFE, "转动角度-byte"),
    变倍停止(0xD1, 0xFF, null),

    设置入网方式(0xD2, 0x01, "数据-byte;"),
    设置视频码流(0xD2, 0x05, "水平像素-short;垂直像素-short;帧率-byte;码率-short;"),
    切换SIM卡(0xD2, 0x06, "数据-byte;"),
    打开关闭AI识别(0xD2, 0x10, "数据-byte;"),
    链路设置(0xD2, 0x1F, "数据-byte;"),
    返回码(0xD2, 0xFF, null),

    喊话模式切换(0xD3, 0x01, "数据-byte;"),
    实时喊话(0xD3, 0x02, "音频数据-base64;"),
    录音喊话(0xD3, 0x03, "音频数据-base64;"),
    文字喊话(0xD3, 0x04, "语速设置-byte;声音-byte;文字数据-bytes;"),
    设置音量(0xD3, 0x05, "音量-byte;"),
    设置循环播放(0xD3, 0x06, "数据-byte;"),
    停止喊话(0xD3, 0xFF, null),

    机场任务完成通知(0xD5, 0xFE, null),

    开关灯(0xD6, 0x01, "动作数据-byte;"),
    开关爆闪(0xD6, 0x02, "动作数据-byte;"),
    功率限制开关(0xD6, 0x03, "动作数据-byte;"),
    亮度设置(0xD6, 0x04, "动作数据-byte;"),
    设置俯仰角(0xD6, 0x05, "动作数据-byte;"),

    MOP数据透传(0xDC, 0, "数据-bytes;"),

    解锁开关(0xDD, 0x01, "动作数据-byte;"),
    点火开关(0xDD, 0x02, "动作数据-byte;"),
    喷火开关(0xDD, 0x03, "动作数据-byte;"),
    燃料开关(0xDD, 0x04, "动作数据-byte;"),
    其它开关(0xDD, 0x05, "动作数据-byte;"),
    压力设置(0xDD, 0x06, "动作数据-byte;"),
    档位设置(0xDD, 0x07, "动作数据-byte;"),
    转动控制(0xDD, 0x08, "动作数据-byte;"),
    设置喷火时间(0xDD, 0x09, "动作数据-byte;"),
    避开喷火区开关(0xDD, 0xFE, "动作数据-byte;");

    private final int instruct;

    private final int action;

    private final String args;

    TyjwEnum(int instruct, int action, String args) {
        this.instruct = instruct;
        this.action = action;
        this.args = args;
    }

    public static TyjwEnum getEnum(int instruct, int action) {
        return Arrays.stream(TyjwEnum.values()).filter(el -> instruct == el.getInstruct() && action == el.getAction()).findFirst().orElseThrow(() -> new BusinessException(String.format("未知指令动作: 0x%02X_0x%02X", instruct, action)));
    }
}
