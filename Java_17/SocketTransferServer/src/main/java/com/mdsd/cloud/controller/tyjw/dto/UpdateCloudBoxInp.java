package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-11]
 */
@Getter
@Setter
public class UpdateCloudBoxInp {

    @Schema(description = "云盒SN编号")
    private String boxSn;

    @Schema(description = "云盒名称(20个字符以内)")
    private String boxName;

    @Schema(description = "照片实时回传开关: 0-关闭,1-开启")
    private Integer openImgRtp;

    @Schema(description = "视频包排序开关: 0-关闭,1-开启 (排序会增加视频延时)")
    private Integer openVideoSort;

    @Schema(description = "云端视频存储开关: 0-关闭,1-开启")
    private Integer openVideoStorage;

    @Schema(description = "视频自动推流开关: 0-关闭,1-开启")
    private Integer videoAutoPush;

    @Schema(description = "云盒固件自动升级开关: 0-关闭,1-开启")
    private Integer autoUpdate;

    @Schema(description = "云盒与平台失联 xx 秒后自动返航")
    private Integer lostTime;

    @Schema(description = "低电量自动返航开关: 0-关闭,1-智能计算,xx-指定的返航电量")
    private Integer minHomeBattery;
}
