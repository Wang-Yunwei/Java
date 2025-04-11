package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.controller.diagnosis.vo.EpidemiologicalDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.EpidemiologicalSaveOrUpdateVo;

/**
 * @author huxiaoneng
 */
public interface MedPhepEpidemiologicalService {

    int saveOrUpdate(EpidemiologicalSaveOrUpdateVo epidemiologicalSaveOrUpdateVo);

    EpidemiologicalDetailVo queryByPatientId(String patientId);
}
