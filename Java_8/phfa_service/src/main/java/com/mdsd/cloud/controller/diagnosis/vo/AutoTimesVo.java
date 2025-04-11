package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wcq
 * @date 2021/11/24 10:45
 */
@Data
public class AutoTimesVo {

    @ApiModelProperty(value = "节点编号")
    private String nodeCode;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "时间")
    private String timeValue;

    @ApiModelProperty(value = "是否区间")
    private Boolean isMore;

    @ApiModelProperty(value = "编辑标识")
    private Boolean editable;
}
