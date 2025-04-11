package com.mdsd.cloud.function.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.entity.MedPhepGeneralInspectionsEntity;
import com.mdsd.cloud.function.MedPhepGeneralInspectionsFunction;
import com.mdsd.cloud.mapper.MedPhepGeneralInspectionsMapper;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2021-11-12]
 */
@Component
public class MedPhepGeneralInspectionsFunctionImpl extends ServiceImpl<MedPhepGeneralInspectionsMapper, MedPhepGeneralInspectionsEntity> implements MedPhepGeneralInspectionsFunction {

}
