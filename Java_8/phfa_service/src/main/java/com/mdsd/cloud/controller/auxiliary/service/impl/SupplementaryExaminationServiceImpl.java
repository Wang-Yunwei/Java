package com.mdsd.cloud.controller.auxiliary.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.auxiliary.dto.EstimateDto;
import com.mdsd.cloud.controller.auxiliary.dto.EstimateItemDto;
import com.mdsd.cloud.controller.auxiliary.dto.SupDetailsResultDto;
import com.mdsd.cloud.controller.auxiliary.dto.SupSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.auxiliary.service.SupplementaryExaminationService;
import com.mdsd.cloud.controller.auxiliary.utils.SupUtil;
import com.mdsd.cloud.entity.MedPhepEstimateEntity;
import com.mdsd.cloud.entity.MedPhepEstimateItemEntity;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepPoctEntity;
import com.mdsd.cloud.function.MedPhepEstimateItemFunction;
import com.mdsd.cloud.mapper.MedPhepEstimateMapper;
import com.mdsd.cloud.mapper.MedPhepMedicalRecordsMapper;
import com.mdsd.cloud.mapper.MedPhepPoctMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2021-11-12]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SupplementaryExaminationServiceImpl implements SupplementaryExaminationService {

    @NonNull
    MedPhepEstimateItemFunction medPhepEstimateItemFunction;//评分明细

    @NonNull
    MedPhepEstimateMapper medPhepEstimateMapper;//评分主表

    @NonNull
    MedPhepPoctMapper medPhepPoctMapper;//POCT

    @NonNull
    MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;//病历信息

    /**
     * 辅助检查 - 新增or更新
     */
    @Override
    public boolean supSaveOrUpdate(final SupSaveOrUpdateParamDto supSaveOrUpdateParamDto) {

        boolean result = false;
        if (!ObjectUtils.isEmpty(supSaveOrUpdateParamDto.getMedPhepPoctEntity())) {
            //处理POCT
            if (null != supSaveOrUpdateParamDto.getMedPhepPoctEntity().getId()) {
                //执行更新
                result = this.medPhepPoctMapper.updateById(supSaveOrUpdateParamDto.getMedPhepPoctEntity()) > 0;
            } else {
                //执行新增
                result = this.medPhepPoctMapper.insert(supSaveOrUpdateParamDto.getMedPhepPoctEntity()) > 0;
            }
        }
        //处理评分
        if (!ObjectUtils.isEmpty(supSaveOrUpdateParamDto.getEstimateDto())) {
            final MedPhepEstimateEntity medPhepEstimateEntity = new MedPhepEstimateEntity();
            BeanUtils.copyProperties(supSaveOrUpdateParamDto.getEstimateDto(), medPhepEstimateEntity);
            final MedPhepEstimateEntity ent = this.medPhepEstimateMapper.selectOne(Wrappers.<MedPhepEstimateEntity>lambdaQuery().select(MedPhepEstimateEntity::getId).eq(MedPhepEstimateEntity::getPatientId, medPhepEstimateEntity.getPatientId()).eq(MedPhepEstimateEntity::getName, medPhepEstimateEntity.getName()));
            if (!ObjectUtils.isEmpty(ent)) {
                //执行更新
                medPhepEstimateEntity.setId(ent.getId());
                this.medPhepEstimateMapper.updateById(medPhepEstimateEntity);
            } else {
                //执行新增
                this.medPhepEstimateMapper.insert(medPhepEstimateEntity);
            }
            //处理评分明细
            if (!CollectionUtils.isEmpty(supSaveOrUpdateParamDto.getEstimateDto().getDetails())) {
                final List<MedPhepEstimateItemEntity> mpeList = Lists.newArrayList();
                //清除所有明细后执行新增
                this.medPhepEstimateItemFunction.remove(Wrappers.<MedPhepEstimateItemEntity>lambdaQuery().eq(MedPhepEstimateItemEntity::getEstimateId, medPhepEstimateEntity.getId()));
                for (final EstimateItemDto dto : supSaveOrUpdateParamDto.getEstimateDto().getDetails()) {
                    final MedPhepEstimateItemEntity medPhepEstimateItemEntity = new MedPhepEstimateItemEntity();
                    medPhepEstimateItemEntity.setEstimateId(medPhepEstimateEntity.getId());
                    medPhepEstimateItemEntity.setContent(JSON.toJSONString(dto));
                    mpeList.add(medPhepEstimateItemEntity);
                }
                if (!CollectionUtils.isEmpty(mpeList)) {
                    this.medPhepEstimateItemFunction.saveBatch(mpeList);
                }
            }
        }
        //更新病历信息表中的血糖
        if (null != supSaveOrUpdateParamDto.getBloodglucose() || null != supSaveOrUpdateParamDto.getOtherCheck()) {
            final LambdaUpdateWrapper<MedPhepMedicalRecordsEntity> lambdaUpdate = Wrappers.<MedPhepMedicalRecordsEntity>lambdaUpdate();
            lambdaUpdate.eq(MedPhepMedicalRecordsEntity::getPatientId, supSaveOrUpdateParamDto.getPatientId());
            if (null != supSaveOrUpdateParamDto.getBloodglucose()) {
                lambdaUpdate.set(MedPhepMedicalRecordsEntity::getBloodglucose, supSaveOrUpdateParamDto.getBloodglucose());
            }
            if (null != supSaveOrUpdateParamDto.getOtherCheck()) {
                lambdaUpdate.set(MedPhepMedicalRecordsEntity::getOtherCheck, supSaveOrUpdateParamDto.getOtherCheck());
            }
            this.medPhepMedicalRecordsMapper.update(null, lambdaUpdate);
        }
        return result;
    }

    /**
     * 辅助检查 - 详情
     */
    @Override
    public SupDetailsResultDto supDetails(final String patientId) {

        final SupDetailsResultDto result = new SupDetailsResultDto();

        //处理血糖
        final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity = this.medPhepMedicalRecordsMapper.selectOne(Wrappers.<MedPhepMedicalRecordsEntity>lambdaQuery().select(MedPhepMedicalRecordsEntity::getBloodglucose, MedPhepMedicalRecordsEntity::getOtherCheck).eq(MedPhepMedicalRecordsEntity::getPatientId, patientId));
        result.setBloodglucose(Objects.nonNull(medPhepMedicalRecordsEntity) ? medPhepMedicalRecordsEntity.getBloodglucose() : null);
        //其他检查
        result.setOtherCheck(Objects.nonNull(medPhepMedicalRecordsEntity) ? medPhepMedicalRecordsEntity.getOtherCheck() : null);
        //处理POCT
        final MedPhepPoctEntity medPhepPoctEntity = this.medPhepPoctMapper.selectOne(Wrappers.<MedPhepPoctEntity>lambdaQuery().eq(MedPhepPoctEntity::getPatientId, patientId));
        //处理附件
        final QueryWrapper<MedPhepAttachmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DELETE_FLAG", 0);
        final List<String> businessIds = Lists.newArrayList(patientId);
        if (Objects.nonNull(medPhepPoctEntity)) {
            businessIds.add(medPhepPoctEntity.getId());
        }
        queryWrapper.in("BUSINESS_ID", businessIds);
        final List<MedPhepAttachmentEntity> medPhepAttachmentEntities = new MedPhepAttachmentEntity().selectList(queryWrapper);
        Optional.ofNullable(medPhepAttachmentEntities).filter(list -> list.size() > 0).ifPresent(list -> {
            //心电图
            result.setElectrocardiogramUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> null != ent.getFileType() && ent.getFileType().equals(0)).collect(Collectors.toList())));
            //现场照片
            result.setScenePhotoUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> null != ent.getFileType() && ent.getFileType().equals(1)).collect(Collectors.toList())));
            //现场视频
            result.setSceneVideoUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> null != ent.getFileType() && ent.getFileType().equals(2)).collect(Collectors.toList())));
            //POCT附件
            if (Objects.nonNull(medPhepPoctEntity) && StringUtils.isNotBlank(medPhepPoctEntity.getId())) {
                result.setPoctAttachmentUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> medPhepPoctEntity.getId().equals(ent.getBusinessId())).collect(Collectors.toList())));
            }
        });
        result.setMedPhepPoctEntity(Objects.nonNull(medPhepPoctEntity) ? medPhepPoctEntity : null);
        //处理评分
        final List<MedPhepEstimateEntity> medPhepEstimateEntities = this.medPhepEstimateMapper.selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().eq(MedPhepEstimateEntity::getPatientId, patientId));
        Optional.ofNullable(medPhepEstimateEntities).filter(list -> list.size() > 0).ifPresent(list -> {
            final List<EstimateDto> estimateDtoList = Lists.newArrayList();
            final List<String> collect = list.stream().map(MedPhepEstimateEntity::getId).collect(Collectors.toList());
            final List<MedPhepEstimateItemEntity> medPhepEstimateItemEntities = this.medPhepEstimateItemFunction.list(Wrappers.<MedPhepEstimateItemEntity>lambdaQuery().in(MedPhepEstimateItemEntity::getEstimateId, collect));
            list.forEach(dto -> {
                final EstimateDto estimateDto = new EstimateDto();
                BeanUtils.copyProperties(dto, estimateDto);
                if (!CollectionUtils.isEmpty(medPhepEstimateItemEntities)) {
                    final List<MedPhepEstimateItemEntity> collect1 = medPhepEstimateItemEntities.stream().filter(mpeiEnt -> mpeiEnt.getEstimateId().equals(estimateDto.getId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect1)) {
                        final List<EstimateItemDto> estimateItemDtoList = Lists.newArrayList();
                        collect1.forEach(mpeiEnt -> {
                            if (StringUtils.isNotBlank(mpeiEnt.getContent())) {
                                estimateItemDtoList.add(JSON.parseObject(mpeiEnt.getContent(), EstimateItemDto.class));
                            }
                        });
                        estimateDto.setDetails(estimateItemDtoList);
                    }
                }
                estimateDtoList.add(estimateDto);
            });
            result.setEstimateDto(estimateDtoList);
        });
        return result;
    }
}
