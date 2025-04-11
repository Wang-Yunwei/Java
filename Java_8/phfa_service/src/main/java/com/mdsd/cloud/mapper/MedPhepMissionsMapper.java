package com.mdsd.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.diagnosis.vo.MissionsInfoVo;
import com.mdsd.cloud.controller.diagnosis.vo.PhepMissionsHistoryListVo;
import com.mdsd.cloud.controller.diagnosis.vo.PhepMissionsParamVo;
import com.mdsd.cloud.controller.diagnosis.vo.request.MissionsInfoRequest;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wcq
 */
@Mapper
public interface MedPhepMissionsMapper extends BaseMapper<MedPhepMissionsEntity> {

    List<PhepMissionsHistoryListVo> queryMissionsList(@Param("dto") PhepMissionsParamVo phepMissionsParamVo);

    IPage<MissionsInfoVo> queryByCallTime(Page<MissionsInfoVo> page, @Param("param") MissionsInfoRequest missionsInfoRequest);
}
