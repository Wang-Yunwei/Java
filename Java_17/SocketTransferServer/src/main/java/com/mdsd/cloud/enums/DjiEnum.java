package com.mdsd.cloud.enums;

import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;
import com.mdsd.cloud.response.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author WangYunwei [2025-03-26]
 */
@Getter
public enum DjiEnum {
    云台管理_反初始化(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F0_DEINIT_VALUE, null),
    云台管理_初始化(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F1_INIT_VALUE, null),
    云台管理_设置工作模式(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F2_SET_MODE_VALUE, "{\"mountPosition\":%d,\"mode\":%d}"),
    云台管理_重置角度(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F3_RESET_VALUE, "{\"mountPosition\":%d,\"resetMode\":%d}"),
    云台管理_旋转角度(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F4_ROTATE_VALUE, "{\"mountPosition\":%d,\"rotation\":[%d,%d,%d,%d,%f]}"),
    云台管理_启用或禁用俯仰限位扩展(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F5_SET_PITCH_RANGE_EXTENSION_ENABLED_VALUE, null),
    云台管理_设置最大速度百分比(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F6_SET_CONTROLLER_MAX_SPEED_PERCENTAGE_VALUE, null),
    云台管理_设置云台控制器的平滑因子(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F7_SET_CONTROLLER_SMOOTH_FACTOR_VALUE, null),
    云台管理_恢复出厂设置(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F8_RESTORE_FACTORY_SETTINGS_VALUE, null),

    飞行控制_反初始化(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F0_DEINIT_VALUE, null),
    飞行控制_初始化(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F1_INIT_VALUE, null),
    飞行控制_启用或禁用RTK位置功能(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F2_SET_TRK_POSITION_ENABLE_VALUE, null),
    飞行控制_获取RTK启用状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F3_GET_TRK_POSITION_ENABLE_VALUE, null),
    飞行控制_设置失联动作(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F4_SET_RC_LOST_ACTION_VALUE, null),
    飞行控制_获取遥控器失联动作悬停_降落_返回(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F5_GET_RC_LOST_ACTION_VALUE, null),
    飞行控制_启用或禁用水平视觉障碍物避让前_后_左_右(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F6_SET_HORIZONTAL_VISUAL_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_获取水平视觉障碍物避让前_后_左_右的开关状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F7_GET_HORIZONTAL_VISUAL_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_开启或关闭水平雷达避障功能(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F8_SET_HORIZONTAL_RADAR_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_获取水平雷达避障功能的状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F9_GET_HORIZONTAL_RADAR_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_开启或关闭上视避障功能(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F10_SET_UPWARDS_VISUAL_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_获取上视避障功能的状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F11_GET_UPWARDS_VISUAL_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_开启或关闭向上雷达避障功能(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F12_SET_UPWARDS_RADAR_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_获取向上雷达避障功能的状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F13_GET_UPWARDS_RADAR_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_开启或关闭向下视觉避障功能(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F14_SET_DOWNWARDS_VISUAL_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_获取向下视觉避障功能的状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F15_GET_DOWNWARDS_VISUAL_OBSTACLE_AVOIDANCE_ENABLE_STATUS_VALUE, null),
    飞行控制_紧急制动飞行(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F16_ARREST_FLYING_VALUE, null),
    飞行控制_退出紧急制动状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F17_CANCEL_ARREST_FLYING_VALUE, null),
    飞行控制_无人机在地面时启动电机(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F18_TURN_ON_MOTORS_VALUE, null),
    飞行控制_无人机在地面时关闭电机(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F19_TURN_OFF_MOTORS_VALUE, null),
    飞行控制_在任何情况下紧急停止电机(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F20_EMERGENCY_STOP_OFF_VALUE, null),
    飞行控制_当无人机在地面时请求起飞(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F21_START_TASK_OFF_VALUE, null),
    飞行控制_当无人机在空中时请求降落(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F22_START_LANDING_VALUE, null),
    飞行控制_当无人机正在降落时请求取消降落(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F23_CANCEL_LANDING_VALUE, null),
    飞行控制_当无人机距离地面07米时确认着陆(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F24_START_CONFIRM_LANDING_VALUE, null),
    飞行控制_在任何情况下都强制着陆(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F25_START_FORCE_LANDING_VALUE, null),
    飞行控制_设置自定义GPS非RTK的home点位置(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F26_SET_HOME_LOCATION_USING_GPS_COORDINATES_VALUE, null),
    飞行控制_使用当前飞机的GPS非RTK位置设置home点位置(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F27_SET_HOME_LOCATION_USING_CURRENT_AIRCRAFT_LOCATION_VALUE, null),
    飞行控制_设置返航高度(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F28_SET_GO_HOME_ALTITUDE_VALUE, null),
    飞行控制_获取返航高度(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F29_GET_GO_HOME_ALTITUDE_VALUE, null),
    飞行控制_获取国家码(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F30_GET_COUNTRY_CODE_VALUE, null),
    飞行控制_当无人机在空中时请求返航动作(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F31_START_GO_HOME_VALUE, null),
    飞行控制_在无人机返航时请求取消返航动作(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F32_CANCEL_GO_HOME_VALUE, null),
    飞行控制_获取无人机的摇杆控制权限(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F33_OBTAIN_JOYSTICK_CTRL_AUTHORITY_VALUE, null),
    飞行控制_释放无人机摇杆控制权限(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F34_RELEASE_JOYSTICK_CTRL_AUTHORITY_VALUE, null),
    飞行控制_使用回调函数订阅摇杆控制权限切换事件(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F35_REG_JOYSTICK_CTRL_AUTHORITY_EVENT_CALLBACK_VALUE, null),
    飞行控制_设置摇杆模式(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F36_SET_JOYSTICK_ACTION_VALUE, "{\"horizontalCoordinate\":%d,\"horizontalControlMode\":%d,\"stableControlMode\":%d,\"verticalControlMode\":%d,\"yawControlMode\":%d}"),
    飞行控制_执行摇杆动作(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F37_EXECUTE_JOYSTICK_ACTION_VALUE, "{\"north\":%d,\"east\":%d,\"down\":%d,\"yaw\":%d,\"down_speed\":%d}"),
    飞行控制_执行紧急制动动作(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F38_EXECUTE_EMERGENCY_BRAKE_ACTION_VALUE, null),
    飞行控制_取消紧急制动动作(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F39_CANCEL_EMERGENCY_BRAKE_ACTION_VALUE, null),
    飞行控制_获取飞机的通用信息(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F40_GET_GENERA_INFO_VALUE, null),
    飞行控制_设置启动或禁用失联动作状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F41_SET_RC_LOST_ACTION_ENABLE_STATUS_VALUE, null),
    飞行控制_获取失联动作状态(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F42_GET_ENABEL_RC_LOST_ACTION_STATUS_VALUE, null),
    飞行控制_注册回调函数触发FTS事件(DjiProtoBuf.ModuleEnum.M6_FLIGHT_CONTROLLER_VALUE, DjiProtoBuf.DirectiveEnum.M6_F43_REG_TRIGGER_FTS_EVENT_CALLBACK_VALUE, null),

    感知模块_反初始化(DjiProtoBuf.ModuleEnum.M13_PERCEPTION_VALUE, DjiProtoBuf.DirectiveEnum.M13_F0_DEINIT_VALUE, null),
    感知模块_初始化(DjiProtoBuf.ModuleEnum.M13_PERCEPTION_VALUE, DjiProtoBuf.DirectiveEnum.M13_F1_INIT_VALUE, null),
    感知模块_订阅(DjiProtoBuf.ModuleEnum.M13_PERCEPTION_VALUE, DjiProtoBuf.DirectiveEnum.M13_F2_SUBSCRIBE_PERCEPTION_IMAGE_VALUE, null),
    感知模块_取消订阅(DjiProtoBuf.ModuleEnum.M13_PERCEPTION_VALUE, DjiProtoBuf.DirectiveEnum.M13_F3_UNSUBSCRIBE_PERCEPTION_IMAGE_VALUE, null),
    感知模块_获取参数(DjiProtoBuf.ModuleEnum.M13_PERCEPTION_VALUE, DjiProtoBuf.DirectiveEnum.M13_F4_GET_STEREO_CAMERA_PARAMETERS_VALUE, null),

    航点_反初始化(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F0_DEINIT_VALUE, null),
    航点_上传任务(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F2_UPLOAD_MISSION_VALUE, "{\"missTotalLen\":%d,\"missionList\":%s}"),
    航点_开始任务(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F3_START_VALUE, null),
    航点_停止任务(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F4_STOP_VALUE, null),
    航点_暂停任务(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F5_PAUSE_VALUE, null),
    航点_恢复任务(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F6_RESUME_VALUE, null),
    航点_获取全局巡航速度(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F7_GET_GLOBAL_CRUISE_SPEED_VALUE, null),
    航点_设置全局巡航速度(DjiProtoBuf.ModuleEnum.M15_WAYPOINT_V2_VALUE, DjiProtoBuf.DirectiveEnum.M15_F8_SET_GLOBAL_CRUISE_SPEED_VALUE, null),
    ;

    private final int module;

    private final int directive;

    private final String arguments;

    DjiEnum(int module, int directive, String arguments) {
        this.module = module;
        this.directive = directive;
        this.arguments = arguments;
    }

    public static DjiEnum getEnum(int module, int directive) {
        return Arrays.stream(DjiEnum.values()).filter(el -> module == el.module && directive == el.directive).findFirst().orElseThrow(() -> new BusinessException(String.format("未知指令动作: 0x%02X_0x%02X", module, directive)));
    }
}
