package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;

/**
 * @author huxiaoneng
 */
public interface MedPhepMedicalRecordsService {

    /**
     * 根据ID查询病历信息
     *
     * @param patientId 患者ID
     * @return message
     */
    MedPhepMedicalRecordsEntity queryById(String patientId);

    /**
     * 新增or修改
     *
     * @param medPhepMedicalRecordsEntity 入参
     * @return message
     */
    String saveOrUpdate(MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity);

    /**
     * 根据患者ID新增or修改
     *
     * @param medPhepMedicalRecordsEntity 入参
     * @return message
     */
    int updateByPatientId(MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity);
}
