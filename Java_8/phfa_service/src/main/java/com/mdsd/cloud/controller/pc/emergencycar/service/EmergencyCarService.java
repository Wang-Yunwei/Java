package com.mdsd.cloud.controller.pc.emergencycar.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListPo;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListRo;
import com.mdsd.cloud.entity.MedPhepVehiclesEntity;

import java.util.List;

/**
 * @author WangYunwei [2022-05-24]
 */
public interface EmergencyCarService {

    /**
     * 急救车 - 保存or更新
     *
     * @param medPhepVehiclesEntity 入参
     * @return Boolean
     */
    Boolean eccSaveOrUpdate(MedPhepVehiclesEntity medPhepVehiclesEntity);

    /**
     * 急救车 - 删除
     *
     * @param ids
     * @return
     */
    Boolean eccDelete(List<String> ids);

    /**
     * 急救车 - 分页列表
     *
     * @param eccPageListPo
     * @return IPage<EccPageListRo>
     */
    IPage<EccPageListRo> eccPageList(EccPageListPo eccPageListPo);
}
