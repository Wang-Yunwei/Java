package com.mdsd.cloud.controller.cardnumbermanage.function.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicRegitionRelationEntity;
import com.mdsd.cloud.controller.cardnumbermanage.function.MedClinicRegitionRelationFunction;
import com.mdsd.cloud.controller.cardnumbermanage.mapper.RegitionRelationMapper;
import org.springframework.stereotype.Service;

/**
 * @author hxn
 */
@Service
public class MedClinicRegitionRelationFunctionImpl extends ServiceImpl<RegitionRelationMapper, MedClinicRegitionRelationEntity> implements MedClinicRegitionRelationFunction {
}
