package com.mdsd.cloud.controller.socket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class MissionState {

    @Schema(description = "航线是否暂停中: 0-未暂停,1-暂停中")
    private Byte isPause;

    @Schema(description = "目标航点下标(说明：开始成功到第一个航点之间此值为0，到达第一个航点此值为1，到达第二个航点此值为2，以此类推，航线结束后此值不清空直到下一次开始航线)")
    private Short targetWaypointIndex;

    @Schema(description = "是否航点完成: 1-完成,0-未完成(此值废弃)")
    private Byte isWaypointFinished;

    @Schema(description = "云盒是否发送视频数据: 0-不发送,1-发送")
    private Byte pushVideo;

    @Schema(description = "是否锁定4G: 0-不锁定,1-锁定")
    private Byte lock4g;

    @Schema(description = "是否开通图片自动回传功能: 0-未开通,1-已开通")
    private Byte boxModel;

    @Schema(description = "自动拍照间隔: 0-未开始,3~255间隔时间")
    private Short mapPlay;

    @Schema(description = "网络失联后动作: 0-返回HOME点,1-继续航线")
    private Byte loseAction;

    @Schema(description = "是否打点飞行控制中: 0-否,1-是")
    private Byte isPointControl;

    @Schema(description = "是否UWB精准降落中: 0-不是,1-是")
    private Byte isUwbLanding;

    @Schema(description = "视频是否推流中: 0-不是,1-是")
    private Byte isPushVideoing;

    @Schema(description = "是否二维码精准降落中: 0-不是,1-是")
    private Byte isDLanding;

    @Schema(description = "二维码精准降落模式: 0-载荷降落,1-下视降落")
    private Byte perceptionFlag;

    @Schema(description = "是否指定距离飞行控制中: 0-否,1-是")
    private Byte isPositionControl;

}
