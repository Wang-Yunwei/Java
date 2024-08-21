package com.mdsd.cloud.controller.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)
public class ReplaceBatteryDTO {

    @Schema(description = "机场绑定的云盒编号")
    private String boxSn;

    @Schema(description = "机场编号")
    private String hangarId;

    @Schema(description = "机场类型: 2-换电机库")
    private Byte hangarType;

    @Schema(description = "机场名称")
    private String hangarName;

    @Schema(description = "机场经度")
    private Double hangarLng;

    @Schema(description = "机场纬度")
    private Double hangarLat;

    @Schema(description = "备降点经度")
    private Double secondLng;

    @Schema(description = "备降点纬度")
    private Double secondLat;

    @Schema(description = "机场在线状态: 0-离线,1-在线")
    private Byte online;

    @Schema(description = "舱外视频: 0-无,1-有")
    private Byte outVideo;

    @Schema(description = "舱内视频: 0-无,1-有")
    private Byte inVideo;

    @Schema(description = "机场状态: 0-未知,11-飞起准备,12-起飞准备完成,21-准备降落,22-准备降落完成,31-正在换电,32-正在关仓,100-空闲,101-维护,102-故障")
    private Byte flowState;

    @Schema(description = "机场舱门状态: 0-未知,1-已打开到位,2-正在打开,3-已关闭,4-正在关闭")
    private Byte rootState;

    @Schema(description = "风速 (单位m/s)")
    private Float windSpeed;

    @Schema(description = "风向: 1-北风,2-东北风,3-东风,4-东南风,5-南风,6-西南风,7-西风,8-西北风")
    private Byte windDirection;

    @Schema(description = "是否雨雪天气")
    private Boolean isRaining;

    @Schema(description = "温度 (单位°C)")
    private Float temperature;

    @Schema(description = "湿度 (百分比)")
    private Float humidity;

    @Schema(description = "机场GPS是否有效: 0-无效,1-有效")
    private Byte gpsValid;

    @Schema(description = "机场指令按钮名称及指令号,机场在线时有此值")
    private List<InstructDTO> instructList;
}
