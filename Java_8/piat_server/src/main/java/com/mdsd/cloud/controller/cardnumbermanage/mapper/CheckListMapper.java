package com.mdsd.cloud.controller.cardnumbermanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.cardnumbermanage.dto.CardInfoResultDto;
import com.mdsd.cloud.controller.cardnumbermanage.dto.HisSearchParamDto;
import com.mdsd.cloud.controller.cardnumbermanage.dto.RegitionRelationResultDto;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicCheckListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/17 16:58
 */
@Mapper
public interface CheckListMapper extends BaseMapper<MedClinicCheckListEntity> {
    void insertList(List<MedClinicCheckListEntity> medClinicCheckListEntityList);

    IPage<RegitionRelationResultDto> selectHisPage(Page<RegitionRelationResultDto> hisResultDtoPage, @Param("hisSearchParamDto") HisSearchParamDto hisSearchParamDto);

    CardInfoResultDto selectCardInfoByRegId(String regId);

    List<RegitionRelationResultDto> selectHisPage();
}
