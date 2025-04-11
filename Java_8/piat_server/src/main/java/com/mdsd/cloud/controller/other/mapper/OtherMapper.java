package com.mdsd.cloud.controller.other.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.other.dto.TriagePatientListParamDto;
import com.mdsd.cloud.controller.other.dto.TriagePatientListResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2021-12-23]
 */
@Mapper
public interface OtherMapper {

    /**
     * Other - 分诊患者列表
     *
     * @param dto  入参
     * @param page 分页
     * @return IPage<TriagePatientListResultDto>
     */
    IPage<TriagePatientListResultDto> triagePatientPageList(@Param(value = "dto") TriagePatientListParamDto dto, Page<TriagePatientListResultDto> page);

    /**
     * Other - 患者信息
     *
     * @param regId 入参
     * @return TriagePatientListResultDto
     */
    TriagePatientListResultDto triagePatientDetail(String regId);
}
