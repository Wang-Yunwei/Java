package com.mdsd.cloud.controller.body.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mdsd.cloud.controller.body.dto.GenListParamDto;
import com.mdsd.cloud.entity.MedPhepGeneralInspectionsEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author WangYunwei [2021-11-12]
 */
public interface GeneralInspectionsService {

    /**
     * 体格检查 - 新增or更新
     */
    boolean genSaveOrUpdate(MedPhepGeneralInspectionsEntity medPhepGeneralInspectionsEntity);

    /**
     * 体格检查 - 删除
     */
    boolean genRemove(@RequestBody final List<String> ids);

    /**
     * 体格检查 - 详情
     */
    MedPhepGeneralInspectionsEntity genDetails(@PathVariable final String id);

    /**
     * 体格检查 - 列表
     */
    List<MedPhepGeneralInspectionsEntity> genList(@RequestBody final GenListParamDto genListParamDto);
}
