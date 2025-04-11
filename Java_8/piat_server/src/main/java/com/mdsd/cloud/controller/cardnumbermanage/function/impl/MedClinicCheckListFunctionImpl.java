package com.mdsd.cloud.controller.cardnumbermanage.function.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicCheckListEntity;
import com.mdsd.cloud.controller.cardnumbermanage.function.MedClinicCheckListFunction;
import com.mdsd.cloud.controller.cardnumbermanage.mapper.CheckListMapper;
import org.springframework.stereotype.Service;

/**
 * @author hxn
 */
@Service
public class MedClinicCheckListFunctionImpl extends ServiceImpl<CheckListMapper, MedClinicCheckListEntity> implements MedClinicCheckListFunction {
}
