package com.mdsd.cloud.controller.pc.medicalrecord.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.auxiliary.dto.EstimateDto;
import com.mdsd.cloud.controller.auxiliary.dto.EstimateItemDto;
import com.mdsd.cloud.controller.auxiliary.utils.SupUtil;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListPo;
import com.mdsd.cloud.controller.pc.emergencycar.dto.EccPageListRo;
import com.mdsd.cloud.controller.pc.emergencycar.service.EmergencyCarService;
import com.mdsd.cloud.controller.pc.medicalrecord.dto.*;
import com.mdsd.cloud.controller.pc.medicalrecord.service.MedicalRecordManageService;
import com.mdsd.cloud.entity.*;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.function.MedPhepEstimateItemFunction;
import com.mdsd.cloud.mapper.*;
import com.mdsd.cloud.utils.HandleString;
import com.mdsd.cloud.utils.PrimaryKeyUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-05-13]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordManageServiceImpl implements MedicalRecordManageService {

    @NonNull
    MedicalRecordManageMapper medicalRecordManageMapper;

    @NonNull
    MedPhepPatientsMapper medPhepPatientsMapper;//患者表

    @NonNull
    MedPhepMissionsMapper medPhepMissionsMapper;//任务表

    @NonNull
    MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;//病历表

    @NonNull
    MedPhepVehiclesMapper medPhepVehiclesMapper;//车辆表

    @NonNull
    MedPhepPoctMapper medPhepPoctMapper;//Poct表

    @NonNull
    MedPhepEmergencyOrdersMapper medPhepEmergencyOrdersMapper;//用药表

    @NonNull
    MedPhepConsentsMapper medPhepConsentsMapper;//知情同意

    @NonNull
    MedPhepEpidemiologicalMapper medPhepEpidemiologicalMapper;//新冠流调表

    @NonNull
    MedPhepShiftRecordMapper medPhepShiftRecordMapper;//患者交接表

    @NonNull
    MedPhepEstimateItemFunction medPhepEstimateItemFunction;//评分明细表

    @NonNull
    EmergencyCarService emergencyCarService;//急救车

    @NonNull
    DataDictionaryService dataDictionaryService;//数据字典

    @NonNull
    PrimaryKeyUtil primaryKeyUtil;

    /**
     * 病历管理 - 保存or更新
     */
    @Override
    public Boolean mrmSaveOrUpdate(final MrmSaveOrUpdatePo mrmSaveOrUpdatePo) {

//        System.out.println(ZoneInfo.getDefault());
        Assert.notNull(mrmSaveOrUpdatePo, "入参对象为: NULL");//断言入参对象不为NULL
        final LocalDateTime now = LocalDateTime.now();
        /*任务*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepMissionsEntity())) {
            //判断任务是否终止
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepMissionsEntity().getSuspendReason())) {
                mrmSaveOrUpdatePo.getMedPhepMissionsEntity().setSuspendType("01");
            }
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepMissionsEntity().getMissionId())) {
                mrmSaveOrUpdatePo.getMedPhepMissionsEntity().setCreateTime(now);
                this.medPhepMissionsMapper.updateById(mrmSaveOrUpdatePo.getMedPhepMissionsEntity());
            } else {
                mrmSaveOrUpdatePo.getMedPhepMissionsEntity().setUpdateTime(now);
                this.medPhepMissionsMapper.insert(mrmSaveOrUpdatePo.getMedPhepMissionsEntity());
            }
        }
        /*患者*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepPatientsEntity())) {
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepPatientsEntity().getPatientId())) {
                mrmSaveOrUpdatePo.getMedPhepPatientsEntity().setUpdateTime(now);
                this.medPhepPatientsMapper.updateById(mrmSaveOrUpdatePo.getMedPhepPatientsEntity());
            } else {
                if (StringUtils.isEmpty(mrmSaveOrUpdatePo.getMedPhepPatientsEntity().getName())) {
                    mrmSaveOrUpdatePo.getMedPhepPatientsEntity().setName("无名氏");
                }
                mrmSaveOrUpdatePo.getMedPhepPatientsEntity().setCreateTime(now);
                mrmSaveOrUpdatePo.getMedPhepPatientsEntity().setMissionId(mrmSaveOrUpdatePo.getMedPhepMissionsEntity().getMissionId());
                this.medPhepPatientsMapper.insert(mrmSaveOrUpdatePo.getMedPhepPatientsEntity());
            }
        }
        /*病历*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity())) {
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity().getRecordId())) {
                mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity().setUpdateTime(now);
                this.medPhepMedicalRecordsMapper.updateById(mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity());
            } else {
                mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity().setRecordId(this.primaryKeyUtil.getFlowingWaterId(CommonConstants.MED_PHEP_MEDICAL_RECORDS));//设置流水号主键
                mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity().setCreateTime(now);
                mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity().setPatientId(mrmSaveOrUpdatePo.getMedPhepPatientsEntity().getPatientId());
                mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity().setStatus("01");//01:填写中
                this.medPhepMedicalRecordsMapper.insert(mrmSaveOrUpdatePo.getMedPhepMedicalRecordsEntity());
            }
        }
        /*体格检查*/
        Optional.ofNullable(mrmSaveOrUpdatePo.getMedPhepGeneralInspectionsEntityList()).filter(list -> list.size() > 0).ifPresent(list -> {
            list.forEach(ent -> {
                if (StringUtils.isNotBlank(ent.getId())) {
                    ent.updateById();
                } else {
                    ent.insert();
                }
            });
        });
        /*Poct*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepPoctEntity())) {
            mrmSaveOrUpdatePo.getMedPhepPoctEntity().setCheckTime(now);
            mrmSaveOrUpdatePo.getMedPhepPoctEntity().insertOrUpdate();
        }
        /*评分*/
        if (!ObjectUtils.isEmpty(mrmSaveOrUpdatePo.getEstimateDto())) {
            final MedPhepEstimateEntity medPhepEstimateEntity = new MedPhepEstimateEntity();
            BeanUtils.copyProperties(mrmSaveOrUpdatePo.getEstimateDto(), medPhepEstimateEntity);
            if (Objects.nonNull(medPhepEstimateEntity) && StringUtils.isNotBlank(medPhepEstimateEntity.getId())) {
                medPhepEstimateEntity.updateById();
            } else {
                medPhepEstimateEntity.insert();
            }
            //处理评分明细
            if (!CollectionUtils.isEmpty(mrmSaveOrUpdatePo.getEstimateDto().getDetails())) {
                final List<MedPhepEstimateItemEntity> mpeList = Lists.newArrayList();
                //清除所有明细后执行新增
                this.medPhepEstimateItemFunction.remove(Wrappers.<MedPhepEstimateItemEntity>lambdaQuery().eq(MedPhepEstimateItemEntity::getEstimateId, medPhepEstimateEntity.getId()));
                for (final EstimateItemDto dto : mrmSaveOrUpdatePo.getEstimateDto().getDetails()) {
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
        /*知情同意书*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepConsentsEntity())) {
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepConsentsEntity().getId())) {
                this.medPhepConsentsMapper.updateById(mrmSaveOrUpdatePo.getMedPhepConsentsEntity());
            } else {
                mrmSaveOrUpdatePo.getMedPhepConsentsEntity().setCreateTime(now);
                this.medPhepConsentsMapper.insert(mrmSaveOrUpdatePo.getMedPhepConsentsEntity());
            }
        }
        /*新冠流调*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepEpidemiologicalEntity())) {
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepEpidemiologicalEntity().getId())) {
                this.medPhepEpidemiologicalMapper.updateById(mrmSaveOrUpdatePo.getMedPhepEpidemiologicalEntity());
            } else {
                mrmSaveOrUpdatePo.getMedPhepEpidemiologicalEntity().setCreateTime(now);
                this.medPhepEpidemiologicalMapper.insert(mrmSaveOrUpdatePo.getMedPhepEpidemiologicalEntity());
            }
        }
        /*患者交接*/
        if (Objects.nonNull(mrmSaveOrUpdatePo.getMedPhepShiftRecordEntity())) {
            if (StringUtils.isNotBlank(mrmSaveOrUpdatePo.getMedPhepShiftRecordEntity().getShiftRecordId())) {
                this.medPhepShiftRecordMapper.updateById(mrmSaveOrUpdatePo.getMedPhepShiftRecordEntity());
            } else {
                this.medPhepShiftRecordMapper.insert(mrmSaveOrUpdatePo.getMedPhepShiftRecordEntity());
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 病历管理 - 删除
     */
    @Override
    public Boolean mrmDelete(final List<String> recordIds) {

        Assert.noNullElements(recordIds, "病历ID为: NULL");
        return this.medPhepMedicalRecordsMapper.deleteBatchIds(recordIds) > 0;
    }

    /**
     * 病历管理 - 分页列表
     */
    @Override
    public IPage<MrmPageListRo> mrmPageList(final MrmPageListPo mrmPageListPo) {

        Assert.notNull(mrmPageListPo, "入参对象为: NULL");//断言入参对象不为NULL
        final Page<MrmPageListRo> page = new Page<>(mrmPageListPo.getCurrent(), mrmPageListPo.getSize());
        mrmPageListPo.setPatientName(HandleString.repString(mrmPageListPo.getPatientName()));//患者姓名模糊匹配
        mrmPageListPo.setDiagnosis(HandleString.repString(mrmPageListPo.getDiagnosis()));//初步诊断模糊匹配
        final IPage<MrmPageListRo> result = this.medicalRecordManageMapper.mrmPageList(page, mrmPageListPo);
        Optional.ofNullable(result.getRecords()).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询数据字典: 年龄类别、任务状态、初步诊断、病历状态
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(new GetDictByCodesPao().setCode(Lists.newArrayList(DictionaryEnum.PHFA_0002.getValue(), DictionaryEnum.PHFA_0034.getValue(), DictionaryEnum.PHFA_0057.getValue(), DictionaryEnum.PHFA_0059.getValue())));
            if (!CollectionUtils.isEmpty(dictByCode)) {
                list.forEach(dto -> {
                    if (Objects.nonNull(dto)) {//防止NULL数据
                        //处理年龄
                        if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0002.getValue())) && StringUtils.isNotBlank(dto.getAge()) && StringUtils.isNotBlank(dto.getAgeExtend())) {
                            if (dto.getAgeExtend().equals("01") || dto.getAgeExtend().equals("02") || dto.getAgeExtend().equals("03")) {
                                dto.setAge(String.format("%s%s", dto.getAge(), dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname()));
                            } else {//年龄类别不等于01:岁、02:天、03:月,则直接赋值年龄区间
                                dto.setAge(dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname());
                            }
                        }
                        //处理任务状态
                        if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0034.getValue())) && StringUtils.isNotBlank(dto.getMissionStatus())) {
                            dto.setMissionStatus(dictByCode.get(DictionaryEnum.PHFA_0034.getValue()).stream().filter(ent -> dto.getMissionStatus().equals(ent.getItemcode())).findFirst().get().getItemname());
                        }
                        //处理初步诊断
                        if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0057.getValue())) && StringUtils.isNotBlank(dto.getDiagnosis())) {
                            dictByCode.get(DictionaryEnum.PHFA_0057.getValue()).stream().filter(ent -> dto.getDiagnosis().equals(ent.getItemcode())).findFirst().ifPresent(ent -> {
                                dto.setDiagnosis(ent.getItemname());
                            });
                        }
                        //处理病历状态
                        if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0059.getValue())) && StringUtils.isNotBlank(dto.getMedicalRecordStatus())) {
                            dto.setMedicalRecordStatus(dictByCode.get(DictionaryEnum.PHFA_0059.getValue()).stream().filter(ent -> dto.getMedicalRecordStatus().equals(ent.getItemcode())).findFirst().get().getItemname());
                        }
                    }
                });
            }
        });
        return result;
    }

    /**
     * 病历管理 - 详情
     */
    @Override
    public MrmDetailsRo mrmDetails(final MrmDetailsPo mrmDetailsPo) {

        Assert.notNull(mrmDetailsPo, "入参对象为: NULL");
        final MrmDetailsRo result = new MrmDetailsRo();
        /*急救信息、患者病史*/
        if (StringUtils.isNotBlank(mrmDetailsPo.getPatientId()) && StringUtils.isNotBlank(mrmDetailsPo.getRecordId()) && StringUtils.isNotBlank(mrmDetailsPo.getMissionId())) {
            //患者信息
            result.setMedPhepPatientsEntity(this.medPhepPatientsMapper.selectById(mrmDetailsPo.getPatientId()));
            //病历信息
            result.setMedPhepMedicalRecordsEntity(this.medPhepMedicalRecordsMapper.selectById(mrmDetailsPo.getRecordId()));
            //任务信息
            result.setMedPhepMissionsEntity(this.medPhepMissionsMapper.selectById(mrmDetailsPo.getMissionId()));
        }
        if (StringUtils.isNotBlank(mrmDetailsPo.getPatientId())) {
            /*体格检查*/
            final List<MedPhepGeneralInspectionsEntity> medPhepGeneralInspectionsEntities = new MedPhepGeneralInspectionsEntity().selectList(Wrappers.<MedPhepGeneralInspectionsEntity>lambdaQuery().eq(MedPhepGeneralInspectionsEntity::getPatientId, mrmDetailsPo.getPatientId()).orderByAsc(MedPhepGeneralInspectionsEntity::getCreateTime));
            result.setMedPhepGeneralInspectionsEntityList(medPhepGeneralInspectionsEntities);
            /*辅助检查*/
            final MedPhepPoctEntity medPhepPoctEntity = this.medPhepPoctMapper.selectOne(Wrappers.<MedPhepPoctEntity>lambdaQuery().eq(MedPhepPoctEntity::getPatientId, mrmDetailsPo.getPatientId()));
            result.setMedPhepPoctEntity(medPhepPoctEntity);
            //辅助检查 - 附件
            final QueryWrapper<MedPhepAttachmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("DELETE_FLAG", 0);
            final List<String> businessIds = Lists.newArrayList(mrmDetailsPo.getPatientId());
            if (!Objects.isNull(medPhepPoctEntity) && StringUtils.isNotBlank(medPhepPoctEntity.getId())) {
                businessIds.add(medPhepPoctEntity.getId());
            }
            queryWrapper.in("BUSINESS_ID", businessIds);
            final List<MedPhepAttachmentEntity> medPhepAttachmentEntities = new MedPhepAttachmentEntity().selectList(queryWrapper);
            Optional.ofNullable(medPhepAttachmentEntities).filter(list -> list.size() > 0).ifPresent(list -> {
                //心电图
                result.setEcgUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> null != ent.getFileType() && ent.getFileType().equals(0)).collect(Collectors.toList())));
                //现场照片
                result.setScenePhotoUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> null != ent.getFileType() && ent.getFileType().equals(1)).collect(Collectors.toList())));
                //现场视频
                result.setSceneVideoUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> null != ent.getFileType() && ent.getFileType().equals(2)).collect(Collectors.toList())));
                //POCT附件
                if (Objects.nonNull(medPhepPoctEntity) && StringUtils.isNotBlank(medPhepPoctEntity.getId())) {
                    result.setPoctUrlList(SupUtil.getAttachmentUrl(list.stream().filter(ent -> medPhepPoctEntity.getId().equals(ent.getBusinessId())).collect(Collectors.toList())));
                }
            });
            //辅助检查 - 评分
            final List<MedPhepEstimateEntity> medPhepEstimateEntities = new MedPhepEstimateEntity().selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().eq(MedPhepEstimateEntity::getPatientId, mrmDetailsPo.getPatientId()));
            Optional.ofNullable(medPhepEstimateEntities).filter(list -> list.size() > 0).ifPresent(list -> {
                final List<EstimateDto> estimateDtoList = Lists.newArrayList();
                final List<String> collect = list.stream().map(MedPhepEstimateEntity::getId).collect(Collectors.toList());
                final List<MedPhepEstimateItemEntity> medPhepEstimateItemEntities = new MedPhepEstimateItemEntity().selectList(Wrappers.<MedPhepEstimateItemEntity>lambdaQuery().in(MedPhepEstimateItemEntity::getEstimateId, collect));
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
                result.setEstimateDtoList(estimateDtoList);
            });
        }
        /*诊断处理*/

        /*知情同意、患者交接*/
        if (StringUtils.isNotBlank(mrmDetailsPo.getPatientId()) && StringUtils.isNotBlank(mrmDetailsPo.getMissionId())) {
            //知情同意
            result.setMedPhepConsentsEntity(this.medPhepConsentsMapper.selectOne(Wrappers.<MedPhepConsentsEntity>lambdaQuery().eq(MedPhepConsentsEntity::getPatientId, mrmDetailsPo.getPatientId()).eq(MedPhepConsentsEntity::getMissionId, mrmDetailsPo.getMissionId())));
            //新冠流调
            result.setMedPhepEpidemiologicalEntity(this.medPhepEpidemiologicalMapper.selectOne(Wrappers.<MedPhepEpidemiologicalEntity>lambdaQuery().eq(MedPhepEpidemiologicalEntity::getPatientId, mrmDetailsPo.getPatientId())));
            //患者交接
            result.setMedPhepShiftRecordEntity(this.medPhepShiftRecordMapper.selectOne(Wrappers.<MedPhepShiftRecordEntity>lambdaQuery().eq(MedPhepShiftRecordEntity::getMissionId, mrmDetailsPo.getMissionId()).eq(MedPhepShiftRecordEntity::getPatientId, mrmDetailsPo.getPatientId())));
        }
        return result;
    }

    /**
     * 病历管理 - 获取无任务急救车
     */
    @Override
    public List<String> getNoTaskEmergencyCar(final NoTaskEmergencyCarPo param) {

        final List<String> result = Lists.newArrayList();
        final Long integer = this.medPhepVehiclesMapper.selectCount(Wrappers.<MedPhepVehiclesEntity>lambdaQuery().eq(MedPhepVehiclesEntity::getHospitalId, param.getHospitalId()).eq(MedPhepVehiclesEntity::getHospitalArea, param.getHospitalArea()));
        if (null != integer && integer > 0) {
            final EccPageListPo eccPageListPo = new EccPageListPo().setCurrent(0L).setSize(integer).setHospitalId(param.getHospitalId()).setHospitalArea(param.getHospitalArea());
            eccPageListPo.setCurrent(0L);
            final IPage<EccPageListRo> eccPageListRoIPage = this.emergencyCarService.eccPageList(eccPageListPo);
            Optional.ofNullable(eccPageListRoIPage.getRecords()).filter(list -> list.size() > 0).ifPresent(list -> {
                list.forEach(dto -> {
                    if (0 == dto.getStatus()) {
                        result.add(dto.getLicensePlate());
                    }
                });
            });
        }
        return result;
    }

    /**
     * 病历管理 - 时间轴
     */
    @Override
    public TimeAxisRo timeAxis(final String missionId) {

        Assert.notNull(missionId, "任务ID为: NULL");
        final TimeAxisRo result = this.medicalRecordManageMapper.timeAxis(missionId);
        Optional.ofNullable(result).filter(dto -> Objects.nonNull(result) && StringUtils.isNotBlank(result.getPatientId())).ifPresent(dto -> {
            //首份心电图时间
            final List<MedPhepAttachmentEntity> ecg = new MedPhepAttachmentEntity().selectList(Wrappers.<MedPhepAttachmentEntity>lambdaQuery().select(MedPhepAttachmentEntity::getId, MedPhepAttachmentEntity::getCreatedDate).eq(MedPhepAttachmentEntity::getDeleteFlag, 0).eq(MedPhepAttachmentEntity::getFileType, 0).eq(MedPhepAttachmentEntity::getBusinessId, dto.getPatientId()).orderByAsc(MedPhepAttachmentEntity::getCreatedDate));
            if (!CollectionUtils.isEmpty(ecg)) {
                result.setAttId(ecg.get(0).getId().toString());
                result.setFirstEcgTime(ecg.get(0).getCreatedDate());
            }
            //首次用药时间
            final List<MedPhepEmergencyOrdersEntity> medPhepEmergencyOrdersEntities = this.medPhepEmergencyOrdersMapper.selectList(Wrappers.<MedPhepEmergencyOrdersEntity>lambdaQuery().select(MedPhepEmergencyOrdersEntity::getOrderId, MedPhepEmergencyOrdersEntity::getCreateTime).eq(MedPhepEmergencyOrdersEntity::getPatientId, dto.getPatientId()));
            if (!CollectionUtils.isEmpty(medPhepEmergencyOrdersEntities)) {
                result.setOrderId(medPhepEmergencyOrdersEntities.get(0).getOrderId());
                result.setFirstMedicationTime(medPhepEmergencyOrdersEntities.get(0).getCreateTime());
            }
        });
        return result;
    }

    /**
     * 病历管理 - 修改时间轴
     */
    @Override
    public Boolean timeAxisUpdate(final TimeAxisUpdatePo timeAxisUpdatePo) {

        Assert.notNull(timeAxisUpdatePo, "入参对象为: NULL");
        //病历
        final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity = new MedPhepMedicalRecordsEntity();
        BeanUtils.copyProperties(timeAxisUpdatePo, medPhepMedicalRecordsEntity);
        if (Objects.nonNull(medPhepMedicalRecordsEntity) && StringUtils.isNotBlank(medPhepMedicalRecordsEntity.getRecordId())) {
            this.medPhepMedicalRecordsMapper.updateById(medPhepMedicalRecordsEntity);
        }
        //任务
        final MedPhepMissionsEntity medPhepMissionsEntity = new MedPhepMissionsEntity();
        BeanUtils.copyProperties(timeAxisUpdatePo, medPhepMissionsEntity);
        if (Objects.nonNull(medPhepMedicalRecordsEntity) && StringUtils.isNotBlank(medPhepMissionsEntity.getMissionId())) {
            this.medPhepMissionsMapper.updateById(medPhepMissionsEntity);
        }
        //交接单
        final MedPhepShiftRecordEntity medPhepShiftRecordEntity = new MedPhepShiftRecordEntity();
        BeanUtils.copyProperties(timeAxisUpdatePo, medPhepShiftRecordEntity);
        if (Objects.nonNull(medPhepShiftRecordEntity) && StringUtils.isNotBlank(medPhepShiftRecordEntity.getShiftRecordId())) {
            this.medPhepShiftRecordMapper.updateById(medPhepShiftRecordEntity);
        }
        //附件,更新心电图
        final MedPhepAttachmentEntity medPhepAttachmentEntity = new MedPhepAttachmentEntity();
        BeanUtils.copyProperties(timeAxisUpdatePo, medPhepAttachmentEntity);
        if (Objects.nonNull(medPhepAttachmentEntity) && StringUtils.isNotBlank(timeAxisUpdatePo.getAttId())) {
            medPhepAttachmentEntity.setId(Long.valueOf(timeAxisUpdatePo.getAttId()));
            medPhepAttachmentEntity.setCreatedDate(timeAxisUpdatePo.getFirstEcgTime());
            medPhepAttachmentEntity.updateById();
        }
        //用药
        final MedPhepEmergencyOrdersEntity medPhepEmergencyOrdersEntity = new MedPhepEmergencyOrdersEntity();
        BeanUtils.copyProperties(timeAxisUpdatePo, medPhepEmergencyOrdersEntity);
        if (Objects.nonNull(medPhepEmergencyOrdersEntity) && StringUtils.isNotBlank(medPhepEmergencyOrdersEntity.getOrderId())) {
            medPhepEmergencyOrdersEntity.setCreateTime(timeAxisUpdatePo.getFirstMedicationTime());
            this.medPhepEmergencyOrdersMapper.updateById(medPhepEmergencyOrdersEntity);
        }
        return Boolean.TRUE;
    }
}
