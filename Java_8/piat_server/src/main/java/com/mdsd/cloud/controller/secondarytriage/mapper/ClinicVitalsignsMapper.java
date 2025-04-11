package com.mdsd.cloud.controller.secondarytriage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicVitalsignsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description :二次分诊
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/

@Mapper
public interface ClinicVitalsignsMapper extends BaseMapper<ClinicVitalsignsEntity> {

    /**
     * 插入二次患者记录-生命体征
     *
     * @param clinicVitalsigns
     * @return
     */
    int insertVitalsigns(ClinicVitalsignsEntity clinicVitalsigns);
}