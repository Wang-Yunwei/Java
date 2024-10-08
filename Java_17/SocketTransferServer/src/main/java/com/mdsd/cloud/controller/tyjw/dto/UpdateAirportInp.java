package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class UpdateAirportInp {

    @Schema(description = "机库编号")
    private String hangarId;

    @Schema(description = "机库名称")
    private String hangarName;

    @Schema(description = "云盒编号")
    private String boxSn;

    @Schema(description = "机舱经度(WGS-84),大疆机场修改后不影响降落位置")
    private Double hangarLng;

    @Schema(description = "机舱纬度(WGS-84),大疆机场修改后不影响降落位置")
    private Double hangarLat;

    @Schema(description = "备降点经度(WGS-84),大疆机场修改后不影响降落位置")
    private Double secondLng;

    @Schema(description = "备降点纬度(WGS-84),大疆机场修改后不影响降落位置")
    private Double secondLat;
}
