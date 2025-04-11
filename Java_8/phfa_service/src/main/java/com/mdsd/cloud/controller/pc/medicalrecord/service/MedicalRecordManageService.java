package com.mdsd.cloud.controller.pc.medicalrecord.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.medicalrecord.dto.*;

import java.util.List;

/**
 * @author WangYunwei [2022-05-13]
 */
public interface MedicalRecordManageService {

    /**
     * 病历管理 - 保存or更新
     *
     * @param mrmSaveOrUpdatePo 入参
     * @return Boolean
     */
    Boolean mrmSaveOrUpdate(MrmSaveOrUpdatePo mrmSaveOrUpdatePo);

    /**
     * 病历管理 - 删除
     *
     * @param recordIds 病历ID
     * @return Boolean
     */
    Boolean mrmDelete(final List<String> recordIds);

    /**
     * 病历管理 - 分页列表
     *
     * @param mrmPageListPo 入参
     * @return IPage<MrmPageListRo>
     */
    IPage<MrmPageListRo> mrmPageList(MrmPageListPo mrmPageListPo);

    /**
     * 病历管理 - 详情
     *
     * @param mrmDetailsPo 入参
     * @return MrmDetailsRo
     */
    MrmDetailsRo mrmDetails(MrmDetailsPo mrmDetailsPo);

    /**
     * 病历管理 - 获取无任务急救车
     *
     * @return List<String>
     */
    List<String> getNoTaskEmergencyCar(NoTaskEmergencyCarPo param);

    /**
     * 病历管理 - 时间轴
     *
     * @param missionId 任务ID
     * @return TimeAxisRo
     */
    TimeAxisRo timeAxis(String missionId);

    /**
     * 病历管理 - 修改时间轴
     *
     * @param timeAxisUpdatePo 入参
     * @return Boolean
     */
    Boolean timeAxisUpdate(TimeAxisUpdatePo timeAxisUpdatePo);
}
