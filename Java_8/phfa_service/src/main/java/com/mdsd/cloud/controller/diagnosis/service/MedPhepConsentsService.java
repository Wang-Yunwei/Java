package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.controller.diagnosis.vo.ConsentsDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ConsentsSaveOrUpdateVo;
import com.mdsd.cloud.entity.MedPhepConsentsEntity;

/**
 * @author huxiaoneng
 */
public interface MedPhepConsentsService {

    /**
     * 新增OR修改
     *
     * @param consentsSaveOrUpdateVo 入参
     * @return message
     */
    int saveOrUpdate(ConsentsSaveOrUpdateVo consentsSaveOrUpdateVo);

    /**
     * 根据ID查询
     *
     * @param patientId 患者ID
     * @return message
     */
    MedPhepConsentsEntity queryById(String patientId);

    /**
     * 根据ID查询详情
     *
     * @param patientId 患者ID
     * @return message
     */
    ConsentsDetailVo queryDetailByPatientId(String patientId);
}
