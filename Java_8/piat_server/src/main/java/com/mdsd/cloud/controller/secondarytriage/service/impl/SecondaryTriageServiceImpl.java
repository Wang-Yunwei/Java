package com.mdsd.cloud.controller.secondarytriage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beust.jcommander.internal.Maps;
import com.mdsd.cloud.controller.secondarytriage.dto.PatientSecondcheckDto;
import com.mdsd.cloud.controller.secondarytriage.dto.SecondPatientDto;
import com.mdsd.cloud.controller.secondarytriage.dto.SecondaryTriageDto;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicSecondcheckEntity;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicVitalsignsEntity;
import com.mdsd.cloud.controller.secondarytriage.mapper.ClinicSecondcheckMapper;
import com.mdsd.cloud.controller.secondarytriage.mapper.ClinicVitalsignsMapper;
import com.mdsd.cloud.controller.secondarytriage.service.SecondaryTriageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wgy
 **/
@Service
@RequiredArgsConstructor
public class SecondaryTriageServiceImpl extends ServiceImpl<ClinicSecondcheckMapper, ClinicSecondcheckEntity> implements SecondaryTriageService {

    @NonNull
    ClinicSecondcheckMapper clinicSecondcheckMapper;

    @NonNull
    ClinicVitalsignsMapper clinicVitalsignsMapper;

    /**
     * 根据患者regId查询全部分诊
     */
    @Override
    public List<PatientSecondcheckDto> getsecondcheck(final String regid) {

        final List<PatientSecondcheckDto> getsecondcheck = this.clinicSecondcheckMapper.getsecondcheckl(regid);
        //获取上次分诊等级，区域
        for (int i = 1; i < getsecondcheck.size(); i++) {
            getsecondcheck.get(i).setYturnresult(getsecondcheck.get(i - 1).getTurnresult());
            getsecondcheck.get(i).setYresetlevel(getsecondcheck.get(i - 1).getResetlevel());
        }
        return getsecondcheck;
    }

    /**
     * 新增二次分诊
     */
    @SneakyThrows
    @Override
    public Boolean addsecondcheck(final SecondaryTriageDto secondarytriagedto) {

        final String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        final LocalDateTime now = LocalDateTime.now();
        final ClinicSecondcheckEntity clinicSecondcheck = new ClinicSecondcheckEntity();
        final ClinicVitalsignsEntity clinicVitalsigns = new ClinicVitalsignsEntity();
        BeanUtils.copyProperties(secondarytriagedto.getClinicSecondcheckDto(), clinicSecondcheck);
        BeanUtils.copyProperties(secondarytriagedto.getClinicVitalsignsDto(), clinicVitalsigns);
        //判断id是否存在，不存在添加，存在修改
        if ("".equals(clinicSecondcheck.getId())) {
            clinicSecondcheck.setId(uuid);
            clinicSecondcheck.setVitalsignsId(uuid);
            clinicVitalsigns.setId(uuid);
            //查看分诊时间是否为空，为空赋值当前时间
            if ("".equals(clinicSecondcheck.getVisitTime()) || clinicSecondcheck.getVisitTime() == null) {
                clinicSecondcheck.setVisitTime(now);
            }
            clinicSecondcheck.setCreateTime(now);
            //插入数据
            final int insert = this.clinicSecondcheckMapper.insertSecondcheck(clinicSecondcheck);
            final int insert1 = this.clinicVitalsignsMapper.insertVitalsigns(clinicVitalsigns);
            return insert > 0 ? true : false || insert1 > 0 ? true : false;
        } else {
            //修改数据,type=1修改主表中的信息，2修改二次分诊信息
            return this.secondPatientRecordModification(clinicSecondcheck, clinicVitalsigns);
        }
    }

    /**
     * 查询二次分诊患者
     */
    @Override
    public Map<String, Object> getPatientSecondcheck(final SecondPatientDto secondpatientdto) {

        final Map<String, Object> result = Maps.newHashMap();
        final List<PatientSecondcheckDto> patientsecondcheckdtos = this.clinicSecondcheckMapper.getPatientSecondcheck(secondpatientdto);
        result.put("list", patientsecondcheckdtos);
        result.put("countl", patientsecondcheckdtos.size());
        return result;
    }

    /**
     * 二次分诊患者记录信息回显
     */
    @Override
    public List<PatientSecondcheckDto> secondaryTriageEcho(final String id, final String type) {

        List<PatientSecondcheckDto> patientSecondcheckDtos = new ArrayList<>(11);
        if ("1".equals(type)) {
            patientSecondcheckDtos = this.clinicSecondcheckMapper.hostSecondaryTriageEcho(id);
        } else if ("2".equals(type)) {
            patientSecondcheckDtos = this.clinicSecondcheckMapper.fromsecondarytriageecho(id);
        }
        return patientSecondcheckDtos;
    }

    /**
     * 二次患者记录修改
     */
    public boolean secondPatientRecordModification(final ClinicSecondcheckEntity clinicSecondcheck, final ClinicVitalsignsEntity clinicVitalsigns) {

        int update = 0;
        int updatel = 0;
        clinicVitalsigns.setId(clinicSecondcheck.getId());
        //修改主表记录数据
        if ("1".equals(clinicSecondcheck.getType())) {
            //修改主表中的等级区域
            update = this.clinicSecondcheckMapper.primaryTableUpdateRegid(clinicSecondcheck);
            updatel = this.clinicSecondcheckMapper.vitalSignsUpdateRegid(clinicVitalsigns);
            //修改二次分诊记录数据
        } else if ("2".equals(clinicSecondcheck.getType())) {
            update = this.clinicSecondcheckMapper.secondcheckUpdate(clinicSecondcheck);
            updatel = this.clinicSecondcheckMapper.vitalSignsUpdateRegid(clinicVitalsigns);

        }
        return update > 0 ? true : false || updatel > 0 ? true : false;
    }

}
