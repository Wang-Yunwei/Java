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
@ApiModel("来院方式统计查询条件")
public class ToHospitalDistributionRequestVo extends BaseDto {

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
