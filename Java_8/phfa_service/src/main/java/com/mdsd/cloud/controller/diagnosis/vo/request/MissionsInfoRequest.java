package com.mdsd.cloud.controller.diagnosis.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author huxiaoneng
 */
@Data
public class MissionsInfoRequest {

    private Date startTime;

    private Date endTime;

    private String name;

    @ApiModelProperty("起始页（从1开始）")
    protected Integer pageNo = 1;

    @ApiModelProperty("每页大小")
    protected Integer pageSize = 10;
}
