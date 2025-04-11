package com.mdsd.cloud.controller.qualityControl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationDetailVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationNumberRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationRequestVo;
import com.mdsd.cloud.controller.qualityControl.entity.MedClinicMedicalNumberEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huxiaoneng
 */
public interface MedClinicMedicalNumberService {

    DoctorPatientRationDetailVo queryByArea(DoctorPatientRationRequestVo doctorPatientRationRequestVo);

    IPage<DoctorPatientRationDetailVo> queryList(DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo);

    Boolean saveOrUpdateByKey(MedClinicMedicalNumberEntity medClinicMedicalNumberEntity);

    void detailExport(DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo, HttpServletResponse response);
}
