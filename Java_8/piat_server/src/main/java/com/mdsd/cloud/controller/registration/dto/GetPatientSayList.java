package com.mdsd.cloud.controller.registration.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2021-11-04]
 */
@Getter
@Setter
public class GetPatientSayList {

    /**
     * 主诉编号
     */
    private String PatientSayid;

    /**
     * 主诉描述信息
     */
    private String description;

    /**
     * 所属专科
     */
    private String specialist;
}
