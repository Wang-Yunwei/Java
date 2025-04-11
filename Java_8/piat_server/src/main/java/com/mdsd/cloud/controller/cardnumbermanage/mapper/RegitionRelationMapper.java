package com.mdsd.cloud.controller.cardnumbermanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.cardnumbermanage.dto.RegitionRelationResultDto;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicRegitionRelationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/22 9:58
 */
@Mapper
public interface RegitionRelationMapper extends BaseMapper<MedClinicRegitionRelationEntity> {
    void insertList(List<MedClinicRegitionRelationEntity> medClinicRegitionRelationEntityArrayList);

    List<RegitionRelationResultDto> selectListByRegId(String regId);
}
