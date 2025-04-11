package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.entity.MedPhepMeasuresEntity;

import java.util.List;

/**
 * @author huxiaoneng
 */
public interface MedPhepMeasuresService {

    List<MedPhepMeasuresEntity> queryByPatientId(String patientId);

    int deleteMeasures(String id);

    int saveMeasures(MedPhepMeasuresEntity medPhepMeasuresEntity);
}
