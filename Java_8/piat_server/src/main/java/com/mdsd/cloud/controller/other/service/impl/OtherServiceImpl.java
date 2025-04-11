package com.mdsd.cloud.controller.other.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.other.dto.TriagePatientListParamDto;
import com.mdsd.cloud.controller.other.dto.TriagePatientListResultDto;
import com.mdsd.cloud.controller.other.mapper.OtherMapper;
import com.mdsd.cloud.controller.other.service.OtherService;
import com.mdsd.cloud.controller.registration.dto.GetPatientSayList;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCardInfoMapper;
import com.mdsd.cloud.utils.HandleString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author WangYunwei [2021-12-23]
 */
@Service
@RequiredArgsConstructor
public class OtherServiceImpl implements OtherService {

    @NonNull
    OtherMapper otherMapper;

    @NonNull
    MedClinicCardInfoMapper medClinicCardInfoMapper;

    /**
     * Other - 分诊患者列表
     */
    @Override
    public IPage<TriagePatientListResultDto> triagePatientPageList(final TriagePatientListParamDto triagePatientListParamDto) {

        //处理模糊字段
        if (!StringUtils.isEmpty(triagePatientListParamDto.getName())) {
            triagePatientListParamDto.setName(HandleString.repString(triagePatientListParamDto.getName()));
        }
        final Page<TriagePatientListResultDto> page = new Page<>(triagePatientListParamDto.getCurrent(),
                triagePatientListParamDto.getPageSize());
        final IPage<TriagePatientListResultDto> result =
                this.otherMapper.triagePatientPageList(triagePatientListParamDto, page);
        Optional.ofNullable(result.getRecords()).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询知识库主诉
            final List<GetPatientSayList> patientSayList = this.medClinicCardInfoMapper.getPatientSayList();
            if (!CollectionUtils.isEmpty(patientSayList)) {
                list.forEach(dto -> {
                    if (!StringUtils.isEmpty(dto.getSubjective())) {
                        final GetPatientSayList getPatientSayList =
                                patientSayList.stream().filter(psDto -> psDto.getPatientSayid().equals(dto.getSubjective())).findFirst().get();
                        dto.setSubjective(getPatientSayList.getDescription());
                    }
                });
            }
        });
        return result;
    }

    /**
     * Other - 分诊患者详情
     */
    @Override
    public TriagePatientListResultDto triagePatientDetail(final String regId) {

        return this.otherMapper.triagePatientDetail(regId);
    }
}
