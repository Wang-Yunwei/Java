package com.mdsd.cloud.controller.registration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.registration.entity.MedClinicCheckMasterEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WangYunwei [2021-09-16]
 */
@Mapper
public interface MedClinicCheckMasterMapper extends BaseMapper<MedClinicCheckMasterEntity> {

    boolean bindingCardInfoList(List<String> ids, String eventId);
}
