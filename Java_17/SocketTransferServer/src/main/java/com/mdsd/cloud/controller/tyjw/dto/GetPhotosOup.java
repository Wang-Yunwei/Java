package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class GetPhotosOup {

    @Schema(description = "照片编号",example = "4297")
    private Long photoId;

    @Schema(description = "经度",example = "0")
    private Double lng;

    @Schema(description = "纬度",example = "0")
    private Double lat;

    @Schema(description = "高度 (相对高度)",example = "-8.4E-05")
    private Integer height;

    @Schema(description = "任务编号",example = "10554")
    private Long taskId;

    @Schema(description = "照片大小 (单位:字节)",example = "10741568")
    private Long fileSize;

    @Schema(description = "图片MD5后的值(32位)",example = "a59fe753ef387aa4c4335ac0a1786a39")
    private String fileMd5;

    @Schema(description = "图片地址,图片名前加 s 为缩略图如sDJI_20220128184436_0004_ZOOM.jpg)",example = "http://uav-cloud-history.wogrid.com/1/photo/10554/DJI_20220128184436_0004_ZOOM.jpg")
    private String imgUrl;

    @Schema(description = "图片拍摄时间",example = "1643366672000")
    private String timeStamp;
}
