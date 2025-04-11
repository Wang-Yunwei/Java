package com.mdsd.cloud.controller.diagnosis.vo;

import com.mdsd.cloud.entity.MedPhepConsentsEntity;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huxiaoneng
 * @ClassName ConsentsSaveOrUpdateVo
 * @Date 2021/11/22 13:39
 */
@ApiModel(value = "知情同意书入参")
@Data
public class ConsentsSaveOrUpdateVo implements Serializable {

    /**
     * 患者信息
     */
    private MedPhepPatientsEntity medPhepPatientsEntity;

    /**
     * 知情同意书信息
     */
    private MedPhepConsentsEntity medPhepConsentsEntity;

    /**
     * 医院信息
     */
    private MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity;

}
