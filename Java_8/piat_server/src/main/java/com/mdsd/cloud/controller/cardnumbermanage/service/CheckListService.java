package com.mdsd.cloud.controller.cardnumbermanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdsd.cloud.controller.cardnumbermanage.dto.*;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicCheckListEntity;

import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/17 16:52
 */
public interface CheckListService extends IService<MedClinicCheckListEntity> {

    List<RegitionRelationResultDto> selectCheckListByRegId(String regId);

    IPage<RegitionRelationResultDto> selectListHis(HisSearchParamDto hisSearchParamDto);

    CardInfoResultDto selectCardInfoByRegId(String regId);

    boolean bindingCard(ParamListDto paramListDto, String status);

    List<RegitionRelationResultDto> selectHis();
}
