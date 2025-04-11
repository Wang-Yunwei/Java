package com.mdsd.cloud.controller.knowledgebase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beust.jcommander.internal.Maps;
import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeCategoryEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.OftenChiefComplaintEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.PatientSayEntity;
import com.mdsd.cloud.controller.knowledgebase.mapper.KnowledgeCategoryMapper;
import com.mdsd.cloud.controller.knowledgebase.mapper.KnowledgeMapper;
import com.mdsd.cloud.controller.knowledgebase.mapper.OftenChiefComplaintMapper;
import com.mdsd.cloud.controller.knowledgebase.mapper.PatientSayMapper;
import com.mdsd.cloud.controller.knowledgebase.service.KnowledgeBaseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wgy
 */
@Service
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategoryEntity> implements KnowledgeBaseService {

    @NonNull
    KnowledgeCategoryMapper knowledgebasemapper;

    @NonNull
    PatientSayMapper patientSayMapper;

    @NonNull
    KnowledgeMapper knowledgeMapper;

    @NonNull
    OftenChiefComplaintMapper oftenChiefComplaintMapper;

    final KnowledgeCategoryEntity knowledgeCategoryEntity = new KnowledgeCategoryEntity();

    final KnowledgeEntity knowledgeEntity = new KnowledgeEntity();

    final PatientSayEntity patientSayEntity = new PatientSayEntity();

    final OftenChiefComplaintEntity oftenChiefComplaintEntity = new OftenChiefComplaintEntity();

    /**
     * 1 :急诊大类 medicinetype=（内科，外壳，儿科）
     * 2：急诊主诉
     * 3：主诉依据
     */
    @Override
    public Map<String, Object> getMedicineType(final String type, final String medicinetype) {

        final Map<String, Object> result = Maps.newHashMap();
        if ("0".equals(type)) {
            //查询内伤，外伤，儿科种类
            final List<KnowledgeCategoryEntity> knowledgeCategoryEntities = this.knowledgebasemapper.getmedicinetypecount();
            result.put("label", knowledgeCategoryEntities);
        } else if ("1".equals(type)) {
            //medicinetype=（内科，外壳，儿科）
            final List<KnowledgeCategoryEntity> medicineType = this.knowledgebasemapper.getMedicineType(medicinetype);
            result.put("GeneralCategory", medicineType);
        } else if ("2".equals(type)) {
            //根据大类CATEGORY_ID 查询关联主诉
            final List<PatientSayEntity> chiefComplaint = this.patientSayMapper.getChiefComplaint(medicinetype);
            result.put("ChiefComplaint", chiefComplaint);
        } else if ("3".equals(type)) {
            //根据主诉查询关联依据
            final List<KnowledgeEntity> basis = this.knowledgeMapper.getBasis(medicinetype);
            result.put("basis", basis);
        } else if ("4".equals(type)) {
            //根据主诉查询关联依据
            final List<KnowledgeEntity> getsystemlevel = this.knowledgeMapper.getsystemlevel(medicinetype);
            result.put("getsystemlevel", getsystemlevel);
        }
        return result;
    }

    /**
     * 常用主诉查询
     */
    @Override
    public Map<String, Object> getOftenKnowledgeBase(final String type, final String medicinetype) {

        final Map<String, Object> result = Maps.newHashMap();
        if ("1".equals(type)) {
            //常用主诉查询
            final List<OftenChiefComplaintEntity> oftenPrincipalComplaint = this.oftenChiefComplaintMapper.getOftenPrincipalComplaint(medicinetype);
            result.put("OftenPrincipalComplaint", oftenPrincipalComplaint);
        } else if ("2".equals(type)) {
            //根据常用主诉查询关联依据
            final List<KnowledgeEntity> patientSayEntities = this.patientSayMapper.oftenPrincipalComplaint(medicinetype);
            result.put("OftenBasis", patientSayEntities);
        }
        return result;
    }

    /**
     * 根据内伤外伤查询全部数据
     */
    @Override
    public List<KnowledgeCategoryEntity> getListKnowledgeBase(final String medicinetype) {

        final List<KnowledgeCategoryEntity> selectlist = this.knowledgebasemapper.getListKnowledgeBase(medicinetype);
        return selectlist;
    }

    /**
     * 知识库搜索
     */
    @Override
    public List<PatientSayEntity> searchknowledgebase(final String medicinetype, final String knowledgeName) {

        List<PatientSayEntity> searchknowledgebase = new ArrayList<>(11);
        //(s == null ||"".equals(s)
        if (knowledgeName == null || "".equals(knowledgeName)) {

            searchknowledgebase.add(new PatientSayEntity());
        } else {
            searchknowledgebase = this.knowledgebasemapper.searchknowledgebase(medicinetype, knowledgeName);
        }
        return searchknowledgebase;
    }

    @Override
    public PatientSayEntity getPatientSay(final String patientSayid) {

        PatientSayEntity patientSay = new PatientSayEntity();
        if (!StringUtils.isEmpty(patientSayid)) {
            patientSay = this.knowledgebasemapper.getPatientSay(patientSayid);
        }
        return patientSay;
    }

}
