package com.mdsd.cloud.controller.qualityControl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationDetailVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationNumberRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationRequestVo;
import com.mdsd.cloud.controller.qualityControl.entity.MedClinicMedicalNumberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Mapper
public interface MedClinicMedicalNumberMapper extends BaseMapper<MedClinicMedicalNumberEntity> {

    IPage<DoctorPatientRationDetailVo> queryList(Page<DoctorPatientRationDetailVo> page,@Param("dto") DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo);

    List<DoctorPatientRationDetailVo> queryList(@Param("dto") DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo);

    int selectPatientCount(DoctorPatientRationRequestVo doctorPatientRationRequestVo);
}
