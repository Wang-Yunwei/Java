package com.mdsd.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.rescuers.dto.PageListOrDetailsPo;
import com.mdsd.cloud.entity.MedPhepRescuersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2022-12-06]
 */
@Mapper
public interface MedPhepRescuersMapper extends BaseMapper<MedPhepRescuersEntity> {

    /**
     * 急救人员 - 分页
     */
    IPage<MedPhepRescuersEntity> pageList(@Param("page") Page<MedPhepRescuersEntity> page, @Param("dto") PageListOrDetailsPo param);
}
