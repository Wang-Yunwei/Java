package com.mdsd.cloud.controller.diagnosis.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author huxiaoneng
 */
@Data
public class PhepMissionsParamVo {

    private Date startTime;

    private Date endTime;

    private String licensePlate;
}
