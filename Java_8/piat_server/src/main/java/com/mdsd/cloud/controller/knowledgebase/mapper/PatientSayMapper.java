package com.mdsd.cloud.controller.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.PatientSayEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */
@Mapper
public interface PatientSayMapper extends BaseMapper<PatientSayEntity> {


    /**
     * 主诉-依据查询（type=2 主诉，3 依据）
     *
     * @param categoryid
     * @return
     */
    List<PatientSayEntity> getChiefComplaint(String categoryid);

    /**
     * 常用主诉查询
     *
     * @param categoryid
     * @return
     */
    List<PatientSayEntity> getOftenPrincipalComplaint(String categoryid);


    /**
     * 常用主诉修改
     *
     * @param categoryid
     * @return
     */
    List<KnowledgeEntity> oftenPrincipalComplaint(String categoryid);
}