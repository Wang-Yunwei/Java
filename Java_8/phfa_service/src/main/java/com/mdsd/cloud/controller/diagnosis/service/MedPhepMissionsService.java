package com.mdsd.cloud.controller.diagnosis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.diagnosis.vo.*;
import com.mdsd.cloud.controller.diagnosis.vo.request.MissionsInfoRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsDetailRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsRequest;
import com.mdsd.cloud.controller.diagnosis.vo.request.PhepMissionsUpdateStatusRequest;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;

import java.util.List;

/**
 * @author wcq
 */
public interface MedPhepMissionsService {

    PhepMissionsInitVo init(MedPhepMissionsEntity medPhepMissionsEntity);

    void save(MedPhepMissionsEntity medPhepMissionsEntity);

    void update(MedPhepMissionsEntity medPhepMissionsEntity);

    void deleteById(String id);

    MedPhepMissionsEntity queryById(String missionId);

    MedPhepMissionsEntity queryByLicensePlate(PhepMissionsRequest phepMissionsRequest);

    void updateStatus(PhepMissionsUpdateStatusRequest phepMissionsUpdateStatusRequest);

    PhepMissionsDetailVo detail(PhepMissionsDetailRequest phepMissionsDetailRequest);

    List<PhepMissionsHistoryListVo> queryMissionsList(PhepMissionsParamVo phepMissionsParamVo);

    /**
     * 分诊系统查询病历信息
     */
    IPage<MissionsInfoVo> queryByCallTime(MissionsInfoRequest missionsInfoRequest);
}
