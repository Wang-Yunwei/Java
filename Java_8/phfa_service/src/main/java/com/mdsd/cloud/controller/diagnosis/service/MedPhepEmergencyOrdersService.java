package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.entity.MedPhepEmergencyOrdersEntity;

import java.util.List;

/**
 * @author huxiaoneng
 */
public interface MedPhepEmergencyOrdersService {

    boolean saveOrUpdate(MedPhepEmergencyOrdersEntity medPhepEmergencyOrdersEntity);

    List<MedPhepEmergencyOrdersEntity> queryByPatientId(String patientId);

    int deleteEmergencyOrdersById(String orderId);
}
