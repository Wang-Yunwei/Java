package com.mdsd.cloud.controller.map.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mdsd.cloud.controller.map.dto.RecordTrackPo;
import com.mdsd.cloud.controller.map.dto.SmcListOrDetailPo;
import com.mdsd.cloud.controller.map.dto.SmcSaveOrUpdatePo;
import com.mdsd.cloud.entity.MedPhepGpsInfosEntity;

import java.util.List;

/**
 * @author WangYunwei [2021-11-15]
 */
public interface SpecialtyMapService extends IService<MedPhepGpsInfosEntity> {

    /**
     * 专科地图 - 记录轨迹
     *
     * @param recordTrackPo
     * @return Boolean
     */
    Boolean recordTrack(RecordTrackPo recordTrackPo);

    /**
     * 专科地图 - 保存or更新
     *
     * @param smcSaveOrUpdatePo 入参
     * @return Boolean
     */
    Boolean smcSaveOrUpdate(SmcSaveOrUpdatePo smcSaveOrUpdatePo);

    /**
     * 专科地图 - 移除
     *
     * @param missionIds 任务ID集合
     * @return Boolean
     */
    Boolean smcRemove(List<String> missionIds);

    /**
     * 专科地图 - 查询列表or详情
     *
     * @param smcListOrDetailPo 入参
     * @return Object
     */
    Object smcListOrDetail(SmcListOrDetailPo smcListOrDetailPo);

    /**
     * REDIS - 根据MissionId删除
     *
     * @param missionId 任务ID
     * @return Boolean
     */
    Boolean redisRemoveByMissionId(String missionId);
}
