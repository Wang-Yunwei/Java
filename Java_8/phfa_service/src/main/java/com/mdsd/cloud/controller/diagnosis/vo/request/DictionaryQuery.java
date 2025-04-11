package com.mdsd.cloud.controller.diagnosis.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wcq
 * @date 2021/9/18 15:51
 */
@Data
public class DictionaryQuery {

    @ApiModelProperty(value = "标识")
    private String code;
    
    @ApiModelProperty(value = "医院ID")
    private String hospitalId;
}
