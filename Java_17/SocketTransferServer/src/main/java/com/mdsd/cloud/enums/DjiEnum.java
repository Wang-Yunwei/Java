package com.mdsd.cloud.enums;

import lombok.Getter;

/**
 * @author WangYunwei [2025-03-26]
 */
@Getter
public enum DjiEnum {

    /* E_DJIWaypointV2MissionFinishedAction */
    DJI_WAYPOINT_V2_FINISHED_NO_ACTION(0, "无进一步操作,飞行器可以通过遥控器进行控制"),
    DJI_WAYPOINT_V2_FINISHED_GO_HOME(1, "当任务结束时返航。如果飞行器距离返航点20米以内，则直接降落"),
    DJI_WAYPOINT_V2_FINISHED_AUTO_LANDING(2, "飞行器将在最后一个航点自动降落"),
    DJI_WAYPOINT_V2_FINISHED_GO_TO_FIRST_WAYPOINT(3, "飞行器将返回第一个航点并悬停"),
    DJI_WAYPOINT_V2_FINISHED_CONTINUE_UNTIL_STOP(4, "当飞行器到达最终航点时,它将继续悬停而不结束任务,操纵杆仍可用于将飞行器沿之前的航点拉回,停止此任务的唯一方法是调用stopMission"),

    /* E_DJIWaypointV2MissionActionWhenRcLost */
    DJI_WAYPOINT_V2_MISSION_STOP_WAYPOINT_V2_AND_EXECUTE_RC_LOST_ACTION(0,"当遥控器信号丢失时停止当前任务,并执行用户在应用程序上选择的遥控器丢失动作"),
    DJI_WAYPOINT_V2_MISSION_KEEP_EXECUTE_WAYPOINT_V2(1,"当遥控器信号丢失时继续执行任务,这意味着即使遥控器信号丢失,无人机也将继续按照预设的航点任务进行操作"),

    /* E_DJIWaypointV2MissionGotoFirstWaypointMode */
    DJI_WAYPOINT_V2_MISSION_GO_TO_FIRST_WAYPOINT_MODE_SAFELY(0,"安全前往航点,如果当前高度低于航点的高度,飞行器将上升到与航点相同的高度,然后,它从当前高度前往航点坐标,并继续调整到航点的高度"),
    DJI_WAYPOINT_V2_MISSION_GO_TO_FIRST_WAYPOINT_MODE_POINT_TO_POINT(1,"从当前飞行器位置直接前往航点,这种方式下,飞行器将直线飞向第一个航点,不特别考虑高度的变化"),


    ;


    private int value;

    private String desc;

    DjiEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
