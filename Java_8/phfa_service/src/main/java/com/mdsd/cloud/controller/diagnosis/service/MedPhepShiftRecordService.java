package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.controller.diagnosis.vo.PrintDeliveryRecordRo;
import com.mdsd.cloud.controller.diagnosis.vo.ShiftRecordDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ShiftSaveOrUpdateVo;
import com.mdsd.cloud.entity.MedPhepShiftRecordEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huxiaoneng
 */
public interface MedPhepShiftRecordService {

    /**
     * 新增or修改
     *
     * @param shiftSaveOrUpdateVo 入参
     */
    void saveOrUpdateShift(ShiftSaveOrUpdateVo shiftSaveOrUpdateVo);

    MedPhepShiftRecordEntity queryById(String patientId);

    ShiftRecordDetailVo queryDetail(String patientId);

    void printShiftRecord(String templateId, String patientId, HttpServletResponse response);

    /**
     * 急救交接单 - 打印交接记录
     *
     * @param patientId 患者ID
     * @return PrintDeliveryRecordRo
     */
    PrintDeliveryRecordRo printDeliveryRecord(String patientId);
}
