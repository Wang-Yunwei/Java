package com.mdsd.cloud.controller.diagnosis.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wcq
 * @date 2021/11/15 17:01
 */
@Data
public class PhepMissionsRequest {

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;
}
