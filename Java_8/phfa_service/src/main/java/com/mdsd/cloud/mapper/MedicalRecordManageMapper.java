package com.mdsd.cloud.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.medicalrecord.dto.MrmPageListPo;
import com.mdsd.cloud.controller.pc.medicalrecord.dto.MrmPageListRo;
import com.mdsd.cloud.controller.pc.medicalrecord.dto.TimeAxisRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2022-05-13]
 */
@Mapper
public interface MedicalRecordManageMapper {

    /**
     * 病历管理 - 分页列表
     *
     * @param page          分页
     * @param mrmPageListPo 入参
     * @return IPage<MrmPageListRo>
     */
    IPage<MrmPageListRo> mrmPageList(@Param("page") Page<MrmPageListRo> page, @Param("dto") MrmPageListPo mrmPageListPo);

    /**
     * 病历管理 - 时间轴
     *
     * @param missionId 任务ID
     * @return TimeAxisRo
     */
    TimeAxisRo timeAxis(String missionId);
}
