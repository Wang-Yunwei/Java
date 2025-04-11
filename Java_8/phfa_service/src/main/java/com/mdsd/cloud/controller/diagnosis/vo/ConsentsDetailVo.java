package com.mdsd.cloud.controller.diagnosis.vo;

import com.mdsd.cloud.entity.MedPhepConsentsEntity;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import lombok.Data;

/**
 * @author huxiaoneng
 */
@Data
public class ConsentsDetailVo {

    private MedPhepConsentsEntity medPhepConsentsEntity;

    private MedPhepPatientsEntity medPhepPatientsEntity;

    private MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity;
}
