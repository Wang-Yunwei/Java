package com.mdsd.cloud.controller.triage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Maps;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.triage.dto.TriListParamDto;
import com.mdsd.cloud.controller.triage.dto.TriListResultDto;
import com.mdsd.cloud.controller.triage.dto.TriSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.triage.entity.MedClinicTriageHelpEntity;
import com.mdsd.cloud.controller.triage.mapper.MedClinicTriageHelpMapper;
import com.mdsd.cloud.controller.triage.service.TriageHelpService;
import com.mdsd.cloud.controller.triage.struct.TriageStruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2021-10-25]
 */
@Service
@RequiredArgsConstructor
public class TriageHelpServiceImpl implements TriageHelpService {

    @NonNull
    MedClinicTriageHelpMapper medClinicTriageHelpMapper;

    @NonNull
    TriageStruct triageStruct;

    /**
     * 体征辅助 - 新增or更新
     */
    @Override
    public boolean triSaveOrUpdate(final TriSaveOrUpdateParamDto triSaveOrUpdateParamDto) {

        int result = 0;
        MedClinicTriageHelpEntity medClinicTriageHelpEntity = null;
        if (StringUtils.isNotBlank(triSaveOrUpdateParamDto.getId())) {
            medClinicTriageHelpEntity = this.medClinicTriageHelpMapper.selectById(triSaveOrUpdateParamDto.getId());
        }
        medClinicTriageHelpEntity = this.triageStruct.toMedClinicTriageHelpEntity(triSaveOrUpdateParamDto);
        if (Objects.nonNull(medClinicTriageHelpEntity)) {
            result = this.medClinicTriageHelpMapper.updateById(medClinicTriageHelpEntity);
        } else {
            result = this.medClinicTriageHelpMapper.insert(medClinicTriageHelpEntity);
        }
        return result > CommonConstants.ZERO;
    }

    /**
     * 体征辅助 - 删除
     */
    @Override
    public boolean triRemove(final List<String> ids) {

        final boolean update =
                new MedClinicTriageHelpEntity().update(Wrappers.<MedClinicTriageHelpEntity>lambdaUpdate().set(MedClinicTriageHelpEntity::getStatus, CommonConstants.ONE).in(MedClinicTriageHelpEntity::getId, ids));
        return update;
    }

    /**
     * 体征辅助 - 详情
     */
    @Override
    public MedClinicTriageHelpEntity triDetails(final String id) {

        return this.medClinicTriageHelpMapper.selectById(id);
    }

    /**
     * 体征辅助 - 列表
     */
    @Override
    public IPage<TriListResultDto> triList(final TriListParamDto TriListParamDto) {

        final Page<TriListResultDto> page = new Page<>(TriListParamDto.getCurrent(), TriListParamDto.getPageSize());
        return this.medClinicTriageHelpMapper.triList(page, TriListParamDto);
    }

    /**
     * 个人登记 - 体征辅助分诊
     */
    @Override
    public Map<String, Object> getTriageHelp() {

        final Map<String, Object> result = Maps.newHashMap();
        final List<MedClinicTriageHelpEntity> medClinicTriageHelpEntities = this.medClinicTriageHelpMapper.selectList(Wrappers.<MedClinicTriageHelpEntity>lambdaQuery().eq(MedClinicTriageHelpEntity::getStatus, CommonConstants.ONE));
        //组装第一层数据
        final List<String> oneCollect =
                medClinicTriageHelpEntities.stream().filter(ent -> StringUtils.isNotBlank(ent.getVitalSignCode())).map(MedClinicTriageHelpEntity::getVitalSignCode).distinct().collect(Collectors.toList());
        oneCollect.forEach(ocEnt -> {
            //组装第二层数据
            final Map<String, Object> twoMap = Maps.newHashMap();
            final List<MedClinicTriageHelpEntity> twoCollect = medClinicTriageHelpEntities.stream().filter(ent -> StringUtils.isNotBlank(ent.getYearGroup()) && ent.getVitalSignCode().equals(ocEnt)).collect(Collectors.toList());
            twoCollect.forEach(toEnt -> {
                //组装第三层数据
                final Map<String, String> threeMap = Maps.newHashMap();
                final List<MedClinicTriageHelpEntity> collect =
                        twoCollect.stream().filter(ent -> ent.getVitalSignCode().equals(ocEnt) && ent.getYearGroup().equals(toEnt.getYearGroup())).collect(Collectors.toList());
                collect.forEach(dto -> {
                    threeMap.put(String.format(CommonConstants.REGISTRATION_INFO_SERVICE_ONE, dto.getLevelBegin(), dto.getLevelEnd()),
                            dto.getLevelCode());
                });
                twoMap.put(toEnt.getYearGroup(), threeMap);
            });
            result.put(ocEnt, twoMap);
        });
        return result;
    }
}
