package com.mdsd.cloud.controller.qualityControl.Vo;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
public class PatientProportionReportRequestVo extends BaseDto {

    private String startTime;

    private String endTime;

    /**
     * 月度查询条件
     */
    private String year;

    /**
     * 年度查询条件
     */
    private String startYear;

    private String endYear;

}
