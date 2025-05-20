package com.mdsd.cloud.enums;

import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;
import com.mdsd.cloud.response.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author WangYunwei [2024-07-16]
 */
@Getter
public enum TyjwEnum {
    请求帧头(null, 0x7479, 0, null),
    回复帧头(null, 0x6A77, 0, null),
    注册(null, 0x01, 0, null),
    心跳(null, 0x02, 0, null),
    状态通知_图片上传完成通知(null, 0x09, 0, null),
    状态通知_云盒开关机通知(null, 0x0A, 0, null),
    无人机实时数据_信道质量(null, 0x0C, 0, null),
    无人机实时数据_状态数据(null, 0xA8, 0, null),
    无人机实时数据_遥测数据(null, 0xA9, 0, null),

    云台转动_基于角度_回中(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x00, "转动角度-byte;"),
    云台转动_基于角度_上(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x01, "转动角度-byte;"),
    云台转动_基于角度_右上(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x02, "转动角度-byte;"),
    云台转动_基于角度_右(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x03, "转动角度-byte;"),
    云台转动_基于角度_右下(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x04, "转动角度-byte;"),
    云台转动_基于角度_下(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x05, "转动角度-byte;"),
    云台转动_基于角度_左下(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x06, "转动角度-byte;"),
    云台转动_基于角度_左(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x07, "转动角度-byte;"),
    云台转动_基于角度_左上(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x08, "转动角度-byte;"),
    云台转动_绝对值控制(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x09, "俯仰-float;平移-float;横滚-float;"),
    相机_变倍加(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x0A, null),
    相机_变倍减(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x0B, null),
    相机_变倍到指定倍数(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x0D, "变倍数值-byte;"),
    相机_变倍复位(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x0F, null),
    航线飞行_航线规划(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2, 0xD1, 0x10, "航线数据-bytes;"),
    手动飞行_起飞(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x11, "飞起高度-float;"),
    手动飞行_返航(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x12, null),
    手动飞行_取消返航(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x13, null),
    手动飞行_降落(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x14, null),
    手动飞行_取消降落(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x15, null),
    航线飞行_开始航线(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2, 0xD1, 0x17, null),
    航线飞行_暂停航线(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2, 0xD1, 0x18, null),
    航线飞行_恢复航线(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2, 0xD1, 0x19, null),
    相机_实时激光测距(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x1A, "数据-byte;"),
    相机_手动激光测距(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x1B, "X点坐标-float;Y点坐标-float;"),
    相机_对焦(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x1B, "X点坐标-float;Y点坐标-float;"),
    云台设置_云台设置跟随模式(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x1C, "数据-byte;"),
    云台控制_云台设置姿态(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0x1D, "数据-byte;"),
    航线飞行_结束航线(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2, 0xD1, 0x20, null),
    无人机设置_设置返航高度(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x21, "数据-byte;"),
    相机_设置相机模式(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x22, "动作参数-byte;"),
    相机_拍照(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x23, null),
    相机_开始录像(DjiProtoBuf.ModuleEnum.M14_LIVE_VIEW, 0xD1, 0x24, null),
    相机_停止录像(DjiProtoBuf.ModuleEnum.M14_LIVE_VIEW, 0xD1, 0x25, null),
    相机_切换视频源(DjiProtoBuf.ModuleEnum.M14_LIVE_VIEW, 0xD1, 0x26, "动作参数-byte;"),
    相机_切换摄像头(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x27, "动作参数-byte;"),
    手动飞行_方向控制(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x28, "前后速度-float;左右速度-float;上下速度-float;偏航角-float;执行时间-short;"),
    手动飞行_强制降落(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x29, null),
    相机_打开单点测温(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x2A, "X点坐标-float;Y点坐标-float;"),
    相机_关闭单点测温(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x2B, null),
    相机_打开区域测温(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x2C, "X1点坐标-float;Y1点坐标-float;X2点坐标-float;Y2点坐标-float;"),
    相机_关闭区域测温(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x2D, null),
    状态通知_航线结束通知(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2, 0xD1, 0x2E, null),
    手动飞行_切换无人机控制权(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x30, "数据-byte;"),
    无人机设置_设置返航点(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x31, "经度-double;纬度-double;"),
    手动飞行_紧急制动(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x32, "数据-byte;"),
    无人机设置_水平避障设置(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x35, "数据-byte;"),
    无人机设置_上避障设置(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x36, "数据-byte;"),
    无人机设置_下避障设置(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x37, "数据-byte;"),
    相机_自动拍照(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x38, "数据-byte;"),
    手动飞行_指点飞行(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x39, "经度-double;纬度-double;高度-float;速度-float;飞行模式-byte;"),
    手动飞行_停止指点飞行(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x3A, null),
    状态通知_无人机准备完成通知(null, 0xD1, 0x3D, null),
    手动飞行_返航到指定机场(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER, 0xD1, 0x3E, "返航高度-short;机库经度-double;机库纬度-double;备降点经度-double;备降点纬度-double;机库ID-bytes;"),
    相机_格式化存储卡(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x40, null),
    相机_打开或关闭照片回传(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0x43, "数据-byte;"),
    云台转动_基于速度(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0xF4, "俯仰-float;平移-float;横滚-float;执行时间-short;"),
    云台转动_基于角度_停止(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER, 0xD1, 0xFE, "转动角度-byte"),
    相机_变倍停止(DjiProtoBuf.ModuleEnum.M3_CAMERA_MANAGER, 0xD1, 0xFF, null),

    无人机设置_设置入网方式(null, 0xD2, 0x01, "数据-byte;"),
    无人机设置_设置视频码流(null, 0xD2, 0x05, "水平像素-short;垂直像素-short;帧率-byte;码率-short;"),
    无人机设置_切换SIM卡(null, 0xD2, 0x06, "数据-byte;"),
    设备_链路设置(null, 0xD2, 0x1F, "数据-byte;"),
    返回码(null, 0xD2, 0xFF, null),

    喊话器_喊话模式切换(null, 0xD3, 0x01, "数据-byte;"),
    喊话器_实时喊话(null, 0xD3, 0x02, "音频数据-base64;"),
    喊话器_录音喊话(null, 0xD3, 0x03, "音频数据-base64;"),
    喊话器_文字喊话(null, 0xD3, 0x04, "语速设置-byte;声音-byte;文字数据-bytes;"),
    喊话器_设置音量(null, 0xD3, 0x05, "音量-byte;"),
    喊话器_设置循环播放(null, 0xD3, 0x06, "数据-byte;"),
    喊话器_停止喊话(null, 0xD3, 0xFF, null),

    状态通知_一键起飞下发通知(null, 0xD5, 0xFA, null),
    状态通知_一键起飞执行结果通知(null, 0xD5, 0xFB, null),
    状态通知_机场任务下发通知(null, 0xD5, 0xFC, null),
    状态通知_机场任务执行错误通知(null, 0xD5, 0xFD, null),
    状态通知_机场任务完成通知(null, 0xD5, 0xFE, null),

    爆闪灯_开关灯(null, 0xD6, 0x01, "动作数据-byte;"),
    爆闪灯_开关爆闪(null, 0xD6, 0x02, "动作数据-byte;"),
    爆闪灯_功率限制开关(null, 0xD6, 0x03, "动作数据-byte;"),
    爆闪灯_亮度设置(null, 0xD6, 0x04, "动作数据-byte;"),
    爆闪灯_设置俯仰角(null, 0xD6, 0x05, "动作数据-byte;"),

    MOP数据透传(null, 0xDC, 0, "数据-bytes;"),

    喷火器_解锁开关(null, 0xDD, 0x01, "动作数据-byte;"),
    喷火器_点火开关(null, 0xDD, 0x02, "动作数据-byte;"),
    喷火器_喷火开关(null, 0xDD, 0x03, "动作数据-byte;"),
    喷火器_燃料开关(null, 0xDD, 0x04, "动作数据-byte;"),
    喷火器_其它开关(null, 0xDD, 0x05, "动作数据-byte;"),
    喷火器_压力设置(null, 0xDD, 0x06, "动作数据-byte;"),
    喷火器_档位设置(null, 0xDD, 0x07, "动作数据-byte;"),
    喷火器_转动控制(null, 0xDD, 0x08, "动作数据-byte;"),
    喷火器_设置喷火时间(null, 0xDD, 0x09, "动作数据-byte;"),
    喷火器_避开喷火区开关(null, 0xDD, 0xFE, "动作数据-byte;");


    private final DjiProtoBuf.ModuleEnum modelEnum;

    private final int instruct;

    private final int action;

    private final String args;

    TyjwEnum(DjiProtoBuf.ModuleEnum modelEnum, int instruct, int action, String args) {
        this.modelEnum = modelEnum;
        this.instruct = instruct;
        this.action = action;
        this.args = args;
    }

    public static TyjwEnum getEnum(int instruct, int action) {
        return Arrays.stream(TyjwEnum.values()).filter(el -> instruct == el.instruct && action == el.action).findFirst().orElseThrow(() -> new BusinessException(String.format("未知指令动作: 0x%02X_0x%02X", instruct, action)));
    }
}


