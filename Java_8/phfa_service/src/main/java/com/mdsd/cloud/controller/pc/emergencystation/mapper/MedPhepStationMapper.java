package com.mdsd.cloud.controller.pc.emergencystation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListPo;
import com.mdsd.cloud.controller.pc.emergencystation.dto.EscPageListRo;
import com.mdsd.cloud.entity.MedPhepStationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2022-05-31]
 */
@Mapper
public interface MedPhepStationMapper extends BaseMapper<MedPhepStationEntity> {

    IPage<EscPageListRo> escPageList(@Param("page") Page<EscPageListRo> page, @Param("dto") EscPageListPo escPageListPo);
}
