package com.mdsd.cloud.controller.other.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.other.dto.TriagePatientListParamDto;
import com.mdsd.cloud.controller.other.dto.TriagePatientListResultDto;

/**
 * @author WangYunwei [2021-12-23]
 */
public interface OtherService {

    /**
     * Other - 分诊患者列表
     *
     * @param triagePatientListParamDto 入参
     * @return IPage<TriagePatientListResultDto>
     */
    IPage<TriagePatientListResultDto> triagePatientPageList(TriagePatientListParamDto triagePatientListParamDto);

    /**
     * Other - 分诊患者详情
     *
     * @param regId 入参
     * @return TriagePatientListResultDto
     */
    TriagePatientListResultDto triagePatientDetail(String regId);
}
