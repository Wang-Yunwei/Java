package com.mdsd.cloud.controller.auxiliary.service;

import com.mdsd.cloud.controller.auxiliary.dto.SupDetailsResultDto;
import com.mdsd.cloud.controller.auxiliary.dto.SupSaveOrUpdateParamDto;

/**
 * @author WangYunwei [2021-11-12]
 */
public interface SupplementaryExaminationService {

    /**
     * 辅助检查 - 新增or更新
     */
    boolean supSaveOrUpdate(SupSaveOrUpdateParamDto supSaveOrUpdateParamDto);

    /**
     * 辅助检查 - 详情
     */
    SupDetailsResultDto supDetails(String patientId);
}
