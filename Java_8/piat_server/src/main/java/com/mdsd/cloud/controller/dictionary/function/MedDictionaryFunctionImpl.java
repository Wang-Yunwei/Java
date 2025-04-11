package com.mdsd.cloud.controller.dictionary.function;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.mapper.MedDictionaryMapper;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2022-04-11]
 */
@Component
public class MedDictionaryFunctionImpl extends ServiceImpl<MedDictionaryMapper, MedDictionaryEntity> implements MedDictionaryFunction {

}
