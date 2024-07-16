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
public class CameraState {

    @Schema(description = "相机模式: 0-未知,1-拍照模式,2-录像模式")
    private Byte mode;

    @Schema(description = "是否录制中: 1-录制中,0-未录制")
    private Byte isRecording;

    @Schema(description = "录制时长,单位:秒")
    private Integer recordDuration;

    @Schema(description = "相机视源: 0=可见,1-变焦,2-红外")
    private Byte source;

    @Schema(description = "相机: 0-FPV,1-相机(1),2-相机(2)")
    private Byte camera;

    @Schema(description = "相机当前变焦倍数")
    private Float zoomfactor;

    @Schema(description = "水平像素(例如：1920)")
    private Short width;

    @Schema(description = "垂直像素(例如：1080)")
    private Short height;

    @Schema(description = "帧率,例如:30")
    private Byte frameRate;

    @Schema(description = "码率(单位：0.001)(例如：6兆设置值为6000)")
    private Byte bitstream;

    @Schema(description = "单点测温状态: 0-非单点测温中,1-单点测温中")
    private Byte pointThermometrying;

    @Schema(description = "区域测温状态: 0-非区域测温中,1-区域测温中")
    private Byte areaThermometrying;

    @Schema(description = "激光测距状态: 0-非激光测距中,1-激光测距中")
    private Byte laserRanging;

}
