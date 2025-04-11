package com.mdsd.cloud.controller.secondarytriage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.secondarytriage.dto.PatientSecondcheckDto;
import com.mdsd.cloud.controller.secondarytriage.dto.SecondPatientDto;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicSecondcheckEntity;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicVitalsignsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description :二次分诊
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/
@Mapper
public interface ClinicSecondcheckMapper extends BaseMapper<ClinicSecondcheckEntity> {

    /**
     * 查询患者二次分诊记录
     *
     * @param regid
     * @return
     */
    List<ClinicSecondcheckEntity> getsecondcheck(@Param("regid") String regid);

    /**
     * 查询二次分诊患者
     *
     * @param secondpatientdto
     * @return
     */
    List<PatientSecondcheckDto> getPatientSecondcheck(SecondPatientDto secondpatientdto);


    /**
     * 根据患者regId查询全部分诊
     *
     * @param regid
     * @return
     */
    List<PatientSecondcheckDto> getsecondcheckl(@Param("regid") String regid);

    /**
     * 根据regid 修改主表数据，生命体征数据
     *
     * @param clinicSecondcheck
     * @return
     */
    int primaryTableUpdateRegid(ClinicSecondcheckEntity clinicSecondcheck);

    /**
     * 根据主表id修改生命体征
     *
     * @param clinicVitalsigns
     * @return
     */
    int vitalSignsUpdateRegid(ClinicVitalsignsEntity clinicVitalsigns);

    /**
     * 修改患者二次分诊记录
     * @param clinicSecondcheck
     * @return
     */
    int secondcheckUpdate(ClinicSecondcheckEntity clinicSecondcheck);

    /**
     * 插入二次患者记录
     *
     * @param clinicSecondcheck
     * @return
     */
    int insertSecondcheck(ClinicSecondcheckEntity clinicSecondcheck);

    /**
     * 二次分诊患者记录信息回显（主表数据）
     *
     * @param id
     * @return
     */
    List<PatientSecondcheckDto> hostSecondaryTriageEcho(String id);

    /**
     * 二次分诊患者记录信息回显（二次分诊表数据）
     *
     * @param id
     * @return
     */
    List<PatientSecondcheckDto> fromsecondarytriageecho(String id);
}
