package com.mdsd.cloud.controller.triage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.triage.dto.TriListParamDto;
import com.mdsd.cloud.controller.triage.dto.TriListResultDto;
import com.mdsd.cloud.controller.triage.dto.TriSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.triage.entity.MedClinicTriageHelpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2021-10-25]
 */
public interface TriageHelpService {

    /**
     * 体征辅助 - 新增or更新
     *
     * @param triSaveOrUpdateParamDto 入参
     * @return boolean
     */
    boolean triSaveOrUpdate(TriSaveOrUpdateParamDto triSaveOrUpdateParamDto);

    /**
     * 体征辅助 - 删除
     *
     * @param ids 入参
     * @return boolean
     */
    boolean triRemove(@RequestBody final List<String> ids);

    /**
     * 体征辅助 - 详情
     *
     * @param id 入参
     * @return MedClinicTriageHelpEntity
     */
    MedClinicTriageHelpEntity triDetails(@PathVariable final String id);

    /**
     * 体征辅助 - 列表
     *
     * @param TriListParamDto 入参
     * @return IPage<TriDetailsResultDto>
     */
    IPage<TriListResultDto> triList(@RequestBody final TriListParamDto TriListParamDto);

    /**
     * 个人登记 - 体征辅助分诊
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTriageHelp();
}
