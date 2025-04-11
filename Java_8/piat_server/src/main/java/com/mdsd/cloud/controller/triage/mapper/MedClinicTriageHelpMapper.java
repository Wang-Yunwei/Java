package com.mdsd.cloud.controller.triage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.triage.dto.TriListParamDto;
import com.mdsd.cloud.controller.triage.dto.TriListResultDto;
import com.mdsd.cloud.controller.triage.entity.MedClinicTriageHelpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2021-10-21]
 */
@Mapper
public interface MedClinicTriageHelpMapper extends BaseMapper<MedClinicTriageHelpEntity> {

    /**
     * 体征辅助 - 列表
     *
     * @param page            分页参数
     * @param TriListParamDto 入参
     * @return IPage<TriListResultDto>
     */
    IPage<TriListResultDto> triList(Page<TriListResultDto> page, @Param("dto") TriListParamDto TriListParamDto);
}
