package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.entity.MedPhepPatientsEntity;

/**
 * @author wcq
 */
public interface MedPhepPatientsService {

    void save(MedPhepPatientsEntity medPhepPatientsEntity);

    void update(MedPhepPatientsEntity medPhepPatientsEntity);

    void deleteById(String id);

    MedPhepPatientsEntity queryById(String missionId);

    MedPhepPatientsEntity queryByPatientId(String patientId);
}
