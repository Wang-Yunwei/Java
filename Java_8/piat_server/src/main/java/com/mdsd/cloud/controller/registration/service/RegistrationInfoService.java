package com.mdsd.cloud.controller.registration.service;

import com.mdsd.cloud.controller.registration.dto.*;
import com.mdsd.cloud.controller.registration.entity.MedClinicVulnusDegreeEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2021-09-13]
 */
public interface RegistrationInfoService {

    /**
     * 个人登记 - 新增or更新
     *
     * @param regSaveOrUpdateParamDto 入参
     * @return boolean
     */
    boolean regSaveOrUpdate(RegSaveOrUpdateParamDto regSaveOrUpdateParamDto);

    /**
     * 个人登进 - 删除
     *
     * @param ids 主键列表
     * @return boolean
     */
    boolean regRemove(List<String> ids);

    /**
     * 个人登记 - 详情
     *
     * @param regId 主键ID
     * @return RegDetailsResultDto
     */
    RegDetailsResultDto regDetails(String regId);

    /**
     * 个人登记 - 列表
     *
     * @param regListsParamDto 入参
     * @return Map<String, Object>
     */
    Map<String, Object> regList(RegListsParamDto regListsParamDto);

    /**
     * 个人登记 - 列表_导出
     *
     * @param regListsParamDto 入参
     * @param response         入参
     */
    void regExport(RegListsParamDto regListsParamDto, HttpServletResponse response);

    /**
     * 个人登记 - 删除_患者评分历史记录
     *
     * @param ids 入参
     * @return boolean
     */
    boolean scoRemove(List<String> ids);

    /**
     * 个人登记 - 列表_患者评分历史记录
     *
     * @param regId      分诊ID
     * @param vulnusType 评分类别
     * @return List<MedClinicVulnusDegreeEntity>
     */
    List<MedClinicVulnusDegreeEntity> getScoreList(String regId, String vulnusType);

    /**
     * 个人登记 - 详情_患者评分历史记录
     *
     * @param vdId 评分主表ID
     * @return List<Object>
     */
    List<Object> getScoreDetails(String vdId);

    /**
     * 个人登记 - 新增or更新_修改分诊分级原因
     *
     * @param modSaveOrUpdateParamDto 入参
     * @return boolean
     */
    boolean modSaveOrUpdate(ModSaveOrUpdateParamDto modSaveOrUpdateParamDto);

    /**
     * 个人登记 - 急诊明细
     *
     * @param triageDetailedParamDto 入参
     */
    void TriageDetailed(TriageDetailedParamDto triageDetailedParamDto);

    /**
     * 个人登记 - 分诊凭条
     *
     * @param triageSlipParamDto 入参
     */
    void triageSlip(TriageSlipParamDto triageSlipParamDto);

    /**
     * 个人登记 - 腕带_列表or绑定or解除
     *
     * @param spireLamellaParamDto 入参
     * @return Object
     */
    Object spireLamella(SpireLamellaParamDto spireLamellaParamDto);

    /**
     * 个人登记 - 腕带_PDF
     *
     * @param printWristbandsParamDto 入参
     */
    void printWristbands(PrintWristbandsParamDto printWristbandsParamDto);
}
