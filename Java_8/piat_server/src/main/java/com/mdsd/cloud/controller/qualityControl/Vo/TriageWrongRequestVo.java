package com.mdsd.cloud.controller.qualityControl.Vo;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
@ApiModel("查询条件")
public class TriageWrongRequestVo extends BaseDto {

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("月度查询条件")
    private String year;

    @ApiModelProperty("开始年度")
    private String startYear;

    @ApiModelProperty("结束年度")
    private String endYear;
}
