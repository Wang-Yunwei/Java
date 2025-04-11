package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-04-28]
 */
@Getter
@Setter
@ApiModel(value = "PrintDeliveryRecordRo对象", description = "急救交接单 - 打印交接记录_返参")
public class PrintDeliveryRecordRo {

    @ApiModelProperty(value = "打印内容")
    private List<Map<String, Object>> items;

    @ApiModelProperty(value = "打印持续时间(单位:秒)")
    private final Integer delayTime = 10;
}
