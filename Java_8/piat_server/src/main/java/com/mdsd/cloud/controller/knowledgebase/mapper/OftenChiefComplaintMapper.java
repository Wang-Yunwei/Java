package com.mdsd.cloud.controller.knowledgebase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.knowledgebase.entity.OftenChiefComplaintEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */
@Mapper
public interface OftenChiefComplaintMapper extends BaseMapper<OftenChiefComplaintEntity> {

    /**
     * 常用主诉查询
     *
     * @param medicinetype
     * @return
     */
    List<OftenChiefComplaintEntity> getOftenPrincipalComplaint(String medicinetype);
}