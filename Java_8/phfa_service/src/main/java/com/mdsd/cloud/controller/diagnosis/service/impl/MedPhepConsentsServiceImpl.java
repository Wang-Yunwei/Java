package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepConsentsService;
import com.mdsd.cloud.controller.diagnosis.vo.ConsentsDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ConsentsSaveOrUpdateVo;
import com.mdsd.cloud.entity.MedPhepConsentsEntity;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.enumeration.ValidEnum;
import com.mdsd.cloud.mapper.MedPhepConsentsMapper;
import com.mdsd.cloud.mapper.MedPhepMedicalRecordsMapper;
import com.mdsd.cloud.mapper.MedPhepPatientsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxiaoneng
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepConsentsServiceImpl implements MedPhepConsentsService {

    private final MedPhepConsentsMapper medPhepConsentsMapper;

    private final MedPhepPatientsMapper medPhepPatientsMapper;

    private final MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;

    /**
     * 新增OR修改
     */
    @Override
    public int saveOrUpdate(final ConsentsSaveOrUpdateVo consentsSaveOrUpdateVo) {

        final MedPhepConsentsEntity medPhepConsentsEntity = consentsSaveOrUpdateVo.getMedPhepConsentsEntity();
        final MedPhepPatientsEntity medPhepPatientsEntity = consentsSaveOrUpdateVo.getMedPhepPatientsEntity();
        final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity = consentsSaveOrUpdateVo.getMedPhepMedicalRecordsEntity();

        //修改患者信息表中姓名，年龄，性别，送往医院
        final MedPhepPatientsEntity patientsEntity = this.medPhepPatientsMapper.selectById(medPhepPatientsEntity.getPatientId());
        if (patientsEntity != null) {
            patientsEntity.setName(medPhepPatientsEntity.getName());
            patientsEntity.setAge(medPhepPatientsEntity.getAge());
            patientsEntity.setGender(medPhepPatientsEntity.getGender());
            //修改患者信息
            this.medPhepPatientsMapper.updateById(patientsEntity);
        }

        //修改病历信息中的送往医院？
        final LambdaQueryWrapper<MedPhepMedicalRecordsEntity> recordsQueryWrapper = new LambdaQueryWrapper<>();
        recordsQueryWrapper.eq(MedPhepMedicalRecordsEntity::getPatientId, medPhepMedicalRecordsEntity.getPatientId());
        final MedPhepMedicalRecordsEntity medicalRecordsEntity = this.medPhepMedicalRecordsMapper.selectOne(recordsQueryWrapper);

        if (medicalRecordsEntity != null) {
            medicalRecordsEntity.setToHospital(medPhepMedicalRecordsEntity.getToHospital());
            this.medPhepMedicalRecordsMapper.updateById(medicalRecordsEntity);
        }
        //查询是否已经存在知情同意书
        final LambdaQueryWrapper<MedPhepConsentsEntity> consentQueryWrapper = new LambdaQueryWrapper<>();
        consentQueryWrapper.eq(MedPhepConsentsEntity::getPatientId, medPhepConsentsEntity.getPatientId());

        final MedPhepConsentsEntity consentsEntity = this.medPhepConsentsMapper.selectOne(consentQueryWrapper);
        medPhepConsentsEntity.setStatus(ValidEnum.VALID.getValue());
        if (consentsEntity != null) {
            return this.medPhepConsentsMapper.update(medPhepConsentsEntity, consentQueryWrapper);
        } else {
            return this.medPhepConsentsMapper.insert(medPhepConsentsEntity);
        }
    }

    /**
     * 根据id查询
     */
    @Override
    public MedPhepConsentsEntity queryById(final String patientId) {

        final LambdaQueryWrapper<MedPhepConsentsEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepConsentsEntity::getPatientId, patientId);
        final MedPhepConsentsEntity medPhepConsentsEntity = this.medPhepConsentsMapper.selectOne(queryWrapper);
        return medPhepConsentsEntity;
    }

    @Override
    public ConsentsDetailVo queryDetailByPatientId(final String patientId) {

        final MedPhepConsentsEntity medPhepConsentsEntity = this.medPhepConsentsMapper.selectOne(Wrappers.<MedPhepConsentsEntity>lambdaQuery()
                .eq(MedPhepConsentsEntity::getPatientId, patientId)
                .eq(MedPhepConsentsEntity::getStatus, ValidEnum.VALID.getValue()));

        final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity = this.medPhepMedicalRecordsMapper.selectOne(Wrappers.<MedPhepMedicalRecordsEntity>lambdaQuery()
                .eq(MedPhepMedicalRecordsEntity::getPatientId, patientId));

        final MedPhepPatientsEntity medPhepPatientsEntity = this.medPhepPatientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery()
                .eq(MedPhepPatientsEntity::getPatientId, patientId));
        final ConsentsDetailVo consentsDetailVo = new ConsentsDetailVo();
        consentsDetailVo.setMedPhepConsentsEntity(medPhepConsentsEntity);
        consentsDetailVo.setMedPhepMedicalRecordsEntity(medPhepMedicalRecordsEntity);
        consentsDetailVo.setMedPhepPatientsEntity(medPhepPatientsEntity);
        return consentsDetailVo;
    }
}
