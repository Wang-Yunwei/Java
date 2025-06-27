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

    /* E_DJIWaypointV2MissionFinishedAction */
//    DJI_WAYPOINT_V2_FINISHED_NO_ACTION(0, "无进一步操作,飞行器可以通过遥控器进行控制"),
//    DJI_WAYPOINT_V2_FINISHED_GO_HOME(1, "当任务结束时返航。如果飞行器距离返航点20米以内，则直接降落"),
//    DJI_WAYPOINT_V2_FINISHED_AUTO_LANDING(2, "飞行器将在最后一个航点自动降落"),
//    DJI_WAYPOINT_V2_FINISHED_GO_TO_FIRST_WAYPOINT(3, "飞行器将返回第一个航点并悬停"),
//    DJI_WAYPOINT_V2_FINISHED_CONTINUE_UNTIL_STOP(4, "当飞行器到达最终航点时,它将继续悬停而不结束任务,操纵杆仍可用于将飞行器沿之前的航点拉回,停止此任务的唯一方法是调用stopMission"),
//
//    /* E_DJIWaypointV2MissionActionWhenRcLost */
//    DJI_WAYPOINT_V2_MISSION_STOP_WAYPOINT_V2_AND_EXECUTE_RC_LOST_ACTION(0,"当遥控器信号丢失时停止当前任务,并执行用户在应用程序上选择的遥控器丢失动作"),
//    DJI_WAYPOINT_V2_MISSION_KEEP_EXECUTE_WAYPOINT_V2(1,"当遥控器信号丢失时继续执行任务,这意味着即使遥控器信号丢失,无人机也将继续按照预设的航点任务进行操作"),
//
//    /* E_DJIWaypointV2MissionGotoFirstWaypointMode */
//    DJI_WAYPOINT_V2_MISSION_GO_TO_FIRST_WAYPOINT_MODE_SAFELY(0,"安全前往航点,如果当前高度低于航点的高度,飞行器将上升到与航点相同的高度,然后,它从当前高度前往航点坐标,并继续调整到航点的高度"),
//    DJI_WAYPOINT_V2_MISSION_GO_TO_FIRST_WAYPOINT_MODE_POINT_TO_POINT(1,"从当前飞行器位置直接前往航点,这种方式下,飞行器将直线飞向第一个航点,不特别考虑高度的变化"),


    云台管理_反初始化(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F0_DEINIT_VALUE, null),
    云台管理_初始化(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F1_INIT_VALUE, null),
    云台管理_设置工作模式(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F2_SET_MODE_VALUE, "{\"mountPosition\":%d,\"mode\":%d}"),
    云台管理_重置俯仰和偏航角(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F3_RESET_VALUE, "{\"mountPosition\":%d,\"rotation\":[%d,%d,%d,%d,%d]}"),
    云台管理_旋转角度(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F4_ROTATE_VALUE, null),
    云台管理_启用或禁用俯仰限位扩展(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F5_SET_PITCH_RANGE_EXTENSION_ENABLED_VALUE, null),
    云台管理_设置最大速度百分比(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F6_SET_CONTROLLER_MAX_SPEED_PERCENTAGE_VALUE, null),
    云台管理_设置云台控制器的平滑因子(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F7_SET_CONTROLLER_SMOOTH_FACTOR_VALUE, null),
    云台管理_恢复出厂设置(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE, DjiProtoBuf.DirectiveEnum.M4_F8_RESTORE_FACTORY_SETTINGS_VALUE, null),
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
