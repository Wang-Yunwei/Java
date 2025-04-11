package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wcq
 * @date 2021/9/22 13:48
 */
@Data
public class DictionaryVo implements Serializable {

    @ApiModelProperty(value = "细项标识")
    private String itemcode;

    @ApiModelProperty(value = "细项名称")
    private String itemname;

    @ApiModelProperty(value = "扩展")
    private String extend;
}
