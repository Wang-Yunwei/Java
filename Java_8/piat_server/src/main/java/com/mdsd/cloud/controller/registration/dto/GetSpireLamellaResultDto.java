package com.mdsd.cloud.controller.registration.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-07-26]
 */
@Getter
@Setter
public class GetSpireLamellaResultDto {

    /**
     * 腕带ID
     */
    private String value;

    /**
     * 腕带名称
     */
    private String label;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 分诊编号
     */
    private String regId;
}
