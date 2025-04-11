package com.mdsd.cloud.controller.consultation.service;

import com.mdsd.cloud.controller.consultation.dto.MccListPo;
import com.mdsd.cloud.controller.consultation.dto.MccListRo;
import com.mdsd.cloud.controller.consultation.dto.MccSaveOrUpdateParDto;

import java.util.List;

/**
 * @author WangYunwei [2021-12-31]
 */
public interface MobileConsultationService {

    /**
     * 移动会诊 - 保存or更新
     */
    String mccSaveOrUpdate(MccSaveOrUpdateParDto mccSaveOrUpdateParDto);

    /**
     * 移动会诊 - 列表
     */
    List<MccListRo> mccList(MccListPo mccListPo);
}
