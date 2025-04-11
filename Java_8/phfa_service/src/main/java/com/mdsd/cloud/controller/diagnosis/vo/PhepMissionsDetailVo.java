package com.mdsd.cloud.controller.diagnosis.vo;

import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import lombok.Data;

/**
 * @author wcq
 * @date 2021/11/26 13:17
 */
@Data
public class PhepMissionsDetailVo {

    private MedPhepMissionsEntity missionsEntity;

    private MedPhepPatientsEntity patientsEntity;

    private MedPhepMedicalRecordsEntity medicalRecordsEntity;
}
