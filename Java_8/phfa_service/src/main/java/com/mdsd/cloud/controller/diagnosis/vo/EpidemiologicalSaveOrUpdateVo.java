package com.mdsd.cloud.controller.diagnosis.vo;

import com.mdsd.cloud.entity.MedPhepEpidemiologicalEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import lombok.Data;

/**
 * @author huxiaoneng
 */
@Data
public class EpidemiologicalSaveOrUpdateVo {

    private MedPhepEpidemiologicalEntity medPhepEpidemiologicalEntity;

    private MedPhepPatientsEntity medPhepPatientsEntity;
}
