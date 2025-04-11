package com.mdsd.cloud.controller.dictionary.service;

import com.mdsd.cloud.controller.dictionary.dto.DdcListOrDetailsPo;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.dto.GetDictCodePo;
import com.mdsd.cloud.controller.dictionary.dto.GetDictCodeRo;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-04-07]
 */
public interface DataDictionaryService {

    /**
     * 数据字典 - 保存or更新
     */
    Boolean ddcSaveOrUpdate(MedDictionaryEntity medDictionaryEntity);

    /**
     * 数据字典 - 删除
     */
    Boolean ddcDelete(List<String> ids);

    /**
     * 数据字典 - 列表or详情(注: 主键ID有值,则默认查详情)
     */
    Object ddcListOrDetails(DdcListOrDetailsPo ddcListOrDetailsPo);

    /**
     * 数据字典 - 批量添加
     */
    Boolean ddcAddBatch(MultipartFile file);

    /**
     * 数据字典 - 根据Codes获取枚举值
     *
     * @return "PHFA_0001": [{"code": "PHFA_0001","describe": "院前急救","hospitalId": "RBAC","label": "男","name": "性别","value": "01"},
     * {"code": "PHFA_0001","describe": "院前急救","hospitalId": "RBAC","label": "女","name": "性别","value": "02"},
     * {"code": "PHFA_0001","describe": "院前急救","hospitalId": "RBAC","label": "不详","name": "性别","value": "03"}]
     */
    Map<String, List<MedDictionaryEntity>> getDictByCode(GetDictByCodesPao getDictByCodesPao);

    /**
     * 数据字典 - 根据Codes获取枚举值_重载
     */
    Map<String, List<MedDictionaryEntity>> getDictByCode(DictionaryEnum... enums);

    /**
     * 数据字典 - 获取字典编码
     */
    List<GetDictCodeRo> getDictCode(GetDictCodePo getDictCodePo);
}
