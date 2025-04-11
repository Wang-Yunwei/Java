package com.mdsd.cloud.controller.diagnosis.service;

import com.mdsd.cloud.entity.MedPhepTemplateEntity;

import java.util.List;

/**
 * @author wcq
 */
public interface MedPhepTemplateService {

    void save(MedPhepTemplateEntity medPhepTemplateEntity);

    void update(MedPhepTemplateEntity medPhepTemplateEntity);

    void deleteById(String id);

    List<MedPhepTemplateEntity> queryList(String tempType);
}
