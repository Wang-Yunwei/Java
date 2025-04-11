package com.mdsd.cloud.controller.secondarytriage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mdsd.cloud.controller.secondarytriage.dto.PatientSecondcheckDto;
import com.mdsd.cloud.controller.secondarytriage.dto.SecondPatientDto;
import com.mdsd.cloud.controller.secondarytriage.dto.SecondaryTriageDto;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicSecondcheckEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description :二次分诊
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/


@Service
public interface SecondaryTriageService extends IService<ClinicSecondcheckEntity> {


    /**
     * 根据患者regId查询全部分诊
     *
     * @param regid
     * @return
     */
    List<PatientSecondcheckDto> getsecondcheck(String regid);

    /**
     * 新增二次分诊
     *
     * @param secondarytriagedto
     * @return
     */
    Boolean addsecondcheck(SecondaryTriageDto secondarytriagedto);

    /**
     * 查询二次分诊患者
     *
     * @param secondpatientdto
     * @return
     */
    Map<String, Object> getPatientSecondcheck(SecondPatientDto secondpatientdto);

    /**
     * 二次分诊患者记录信息回显
     *
     * @param id
     * @param type
     * @return
     */
    List<PatientSecondcheckDto> secondaryTriageEcho(String id, String type);
}
