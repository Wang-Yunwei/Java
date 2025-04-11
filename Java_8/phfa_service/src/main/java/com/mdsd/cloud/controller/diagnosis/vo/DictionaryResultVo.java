package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class DictionaryResultVo {

    /**
     * 细项标识
     */
    private String value;

    /**
     * 细项名称
     */
    private String label;

    private String name;

    @ApiModelProperty(value = "扩展")
    private String extend;
}
