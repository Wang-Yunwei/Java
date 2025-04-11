package com.mdsd.cloud.controller.pc.emergencystation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListPo;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListRo;
import com.mdsd.cloud.entity.MedPhepStationEntity;

import java.util.List;

/**
 * @author WangYunwei [2022-05-31]
 */
public interface EmergencyStationService {

    /**
     * 急救站 - 保存or更新
     *
     * @param medPhepStationEntity
     * @return
     */
    Boolean escSaveOrUpdate(MedPhepStationEntity medPhepStationEntity);

    /**
     * 急救站 - 删除
     *
     * @param ids
     * @return
     */
    Boolean escDelete(List<String> ids);

    /**
     * 急救站 - 分页列表
     *
     * @param escPageListPo
     * @return
     */
    IPage<EscPageListRo> escPageList(EscPageListPo escPageListPo);
}
