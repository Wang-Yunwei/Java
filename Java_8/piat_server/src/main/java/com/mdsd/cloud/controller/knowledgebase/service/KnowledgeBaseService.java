package com.mdsd.cloud.controller.knowledgebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeCategoryEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.PatientSayEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wgy
 */
@Service
public interface KnowledgeBaseService extends IService<KnowledgeCategoryEntity> {

    /**
     * 1 :急诊大类 medicinetype=（内科，外壳，儿科）
     * 2：急诊主诉
     * 3：主诉依据
     */
    Map<String, Object> getMedicineType(String type, String medicinetype);

    /**
     * （type:1内伤外伤，2常用主诉）
     */
    Map<String, Object> getOftenKnowledgeBase(String type, String medicinetype);

    /**
     * 根据内伤外伤查询全部数据
     */
    List<KnowledgeCategoryEntity> getListKnowledgeBase(String medicinetype);

    /**
     * 知识库搜索
     */
    List<PatientSayEntity> searchknowledgebase(String medicinetype, String knowledgeName);

    /**
     * 卒中调用-获取依据
     */
    PatientSayEntity getPatientSay(String patientSayid);
}
