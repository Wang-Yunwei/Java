package com.mdsd.cloud.controller.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.registration.dto.GetPatientSayList;
import com.mdsd.cloud.controller.registration.dto.GetSpireLamellaResultDto;
import com.mdsd.cloud.controller.registration.dto.RegListsParamDto;
import com.mdsd.cloud.controller.registration.dto.RegListsResultDto;
import com.mdsd.cloud.controller.registration.entity.MedClinicCardInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangYunwei [2021-09-13]
 */
@Mapper
public interface MedClinicCardInfoMapper extends BaseMapper<MedClinicCardInfoEntity> {

    /**
     * 个人登记_分诊 - 列表
     *
     * @param dto  入参
     * @param page 分页参数
     * @return IPage<RegListsResultDto>
     */
    IPage<RegListsResultDto> regList(@Param(value = "dto") RegListsParamDto dto, Page<RegListsResultDto> page);

    /**
     * 个人登记 - 知识库主诉
     *
     * @return List<GetPatientSayList>
     */
    List<GetPatientSayList> getPatientSayList();

    List<GetSpireLamellaResultDto> getTagList();
}
