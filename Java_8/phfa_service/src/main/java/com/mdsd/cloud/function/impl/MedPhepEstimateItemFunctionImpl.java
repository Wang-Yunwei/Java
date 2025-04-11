package com.mdsd.cloud.function.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.entity.MedPhepEstimateItemEntity;
import com.mdsd.cloud.function.MedPhepEstimateItemFunction;
import com.mdsd.cloud.mapper.MedPhepEstimateItemMapper;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2022-06-02]
 */
@Component
public class MedPhepEstimateItemFunctionImpl extends ServiceImpl<MedPhepEstimateItemMapper, MedPhepEstimateItemEntity> implements MedPhepEstimateItemFunction {

}
