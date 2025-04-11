package com.mdsd.cloud.controller.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeCategoryEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.PatientSayEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author root
 */
@Mapper
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategoryEntity> {

    /**
     * 根据类型查询全部（内伤，外伤，儿科）
     *
     * @param medicinetype
     * @return
     */
    List<KnowledgeCategoryEntity> getMedicineType(String medicinetype);


    /**
     * 逻辑删除 大类，主诉，依据
     *
     * @param list
     * @param state
     * @param type
     * @return
     */
    int updateKnowledgeCategory(@Param("list") List<String> list, @Param("state") String state, @Param("type") String type);

    /**
     * 根据内伤外伤查询全部数据
     *
     * @param medicinetype
     * @return
     */
    List<KnowledgeCategoryEntity> getListKnowledgeBase(String medicinetype);

    /**
     * 查询内伤，外伤，儿科种类
     *
     * @return
     */
    List<KnowledgeCategoryEntity> getmedicinetypecount();

    /**
     * 知识库搜索
     *
     * @param medicinetype
     * @param knowledgeName
     * @return
     */
    List<PatientSayEntity> searchknowledgebase(@Param("medicinetype") String medicinetype, @Param("knowledgeName") String knowledgeName);

    PatientSayEntity getPatientSay(@Param("medicinetype") String patientSayid);
}
