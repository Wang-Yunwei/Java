package com.mdsd.cloud.controller.pc.rescuers.service;

import com.mdsd.cloud.controller.pc.rescuers.dto.PageListOrDetailsPo;
import com.mdsd.cloud.entity.MedPhepRescuersEntity;

import java.util.List;

/**
 * @author WangYunwei [2022-12-06]
 */
public interface RescuersService {

    /**
     * 急救人员 - 保存or修改
     */
    String saveOrUpdate(MedPhepRescuersEntity param);

    /**
     * 急救人员 - 批量删除
     */
    Boolean batchDeletion(List<String> ids);

    /**
     * 急救人员 - 分页or详情
     */
    Object pageListOrDetails(PageListOrDetailsPo param);

}
