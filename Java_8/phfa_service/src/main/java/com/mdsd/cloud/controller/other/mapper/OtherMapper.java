package com.mdsd.cloud.controller.other.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.other.dto.GetPatientsPagePo;
import com.mdsd.cloud.controller.other.dto.GetPatientsPageRo;
import com.mdsd.cloud.controller.other.dto.GetPatientsPo;
import com.mdsd.cloud.controller.other.dto.OneForFourRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangYunwei [2022-01-06]
 */
@Mapper
public interface OtherMapper {

    /**
     * Other - 患者列表
     *
     * @param page              分页
     * @param getPatientsPagePo 入参
     * @return IPage<GetPatientsPageRo>
     */
    IPage<GetPatientsPageRo> getPatientsPage(@Param("page") Page<GetPatientsPageRo> page, @Param("dto") GetPatientsPagePo getPatientsPagePo);

    /**
     * Other - 患者列表 不分页
     */
    List<GetPatientsPageRo> getPatientsPage(@Param("dto") GetPatientsPo getPatientsPo);

    /**
     * Other - 通过患者ID获取医院ID、任务ID、病历ID
     */
    OneForFourRo oneForFour(String patientId);
}
