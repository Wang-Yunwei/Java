package com.mdsd.cloud.controller.pc.emergencycar.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListPo;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WangYunwei [2022-05-26]
 */
@Mapper
public interface EmergencyCarMapper {

    /**
     * 急救车 - 分页列表
     *
     * @param page          分页
     * @param eccPageListPo 入参
     * @return IPage<EccPageListRo>
     */
    IPage<EccPageListRo> eccPageList(@Param("page") Page<EccPageListRo> page, @Param("dto") EccPageListPo eccPageListPo);

    long getTotal();
}
