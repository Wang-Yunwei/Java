package com.mdsd.cloud.controller.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */
@Mapper
public interface KnowledgeMapper extends BaseMapper<KnowledgeEntity> {

    /**
     * 查询主诉关联依据
     *
     * @param patientsayid
     * @return
     */
    List<KnowledgeEntity> getBasis(String patientsayid);


    /**
     * 查询依据等级
     *
     * @param medicinetype
     * @return
     */
    List<KnowledgeEntity> getsystemlevel(String medicinetype);
}