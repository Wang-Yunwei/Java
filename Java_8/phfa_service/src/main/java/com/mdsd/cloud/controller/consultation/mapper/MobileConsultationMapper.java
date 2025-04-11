package com.mdsd.cloud.controller.consultation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.consultation.dto.MccDetailsRo;
import com.mdsd.cloud.controller.consultation.dto.MccListPo;
import com.mdsd.cloud.controller.consultation.dto.MccListRo;
import com.mdsd.cloud.entity.MedPhepMobileConsultationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WangYunwei [2021-12-31]
 */
@Mapper
public interface MobileConsultationMapper extends BaseMapper<MedPhepMobileConsultationEntity> {

    /**
     * 移动会诊 - 获取患者列表中五大专科患者或移动会诊列表中的患者
     *
     * @param mccListPo 入参
     * @return List<MccListRo>
     */
    List<MccListRo> mccList(MccListPo mccListPo);

    /**
     * 移动会诊 - 患者详情
     *
     * @param patientId 入参
     * @return PatientDetailRo
     */
    MccDetailsRo patientDetail(String patientId);
}
