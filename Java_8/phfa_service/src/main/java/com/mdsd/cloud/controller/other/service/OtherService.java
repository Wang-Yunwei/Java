package com.mdsd.cloud.controller.other.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.other.dto.*;

import java.util.List;

/**
 * @author WangYunwei [2022-01-06]
 */
public interface OtherService {

    /**
     * Other - 急救病历
     */
    GetFirstAidMedicalRecordRso getFirstAidMedicalRecord(GetFirstAidMedicalRecordPao getFirstAidMedicalRecordPo);

    /**
     * Other - 患者列表
     */
    IPage<GetPatientsPageRo> getPatientsPage(GetPatientsPagePo getPatientsPagePo);

    /**
     * Other - 急救详情
     */
    FirstAidDetailsRo firstAidDetails(FirstAidDetailsPo firstAidDetailsPo);

    /**
     * Other - 查询患者列表
     */
    List<GetPatientsPageRo> getPatients(GetPatientsPo getPatientsPo);

    /**
     * Other - 通过患者ID获取医院ID、任务ID、病历ID
     */
    OneForFourRo oneForFour(String patientId);

    List<EstimateDto> getEstimateDetail(String missionId);
}
