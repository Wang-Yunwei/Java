package com.mdsd.cloud.controller.groupevents.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.groupevents.dto.*;
import com.mdsd.cloud.controller.groupevents.entity.MedClinicGroupEventsEntity;
import com.mdsd.cloud.controller.groupevents.entity.TemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/13 16:28
 */
@Mapper
public interface MedClinicGroupEventsMapper extends BaseMapper<MedClinicGroupEventsEntity> {

    IPage<CardInfoDetailsDto> gesSelectByIdList(Page<CardInfoDetailsDto> cardInfoResultDtoPage, @Param("gesDetailsParamDto") GesDetailsParamDto gesDetailsParamDto);

    boolean disassociate(List<String> ids);

    void disassociateByEventId(List<String> ids);

    List<GroupEventsExportResultDto> selectByKey(GesDetailsParamDto gesDetailsParamDto);

    /**
     * @param dto  入参
     * @param page 分页参数
     * @return IPage<RegListsResultDto>
     */
    IPage<RegitionListsResultDto> regList(@Param(value = "dto") RegitionListsParamDto dto, Page<RegitionListsResultDto> page);

    /**
     * 打印模板数据
     *
     * @return List<TemplateEntity>
     */
    List<TemplateEntity> getTemplateList(String templateId);
}
