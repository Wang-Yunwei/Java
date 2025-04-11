package com.mdsd.cloud.controller.other.service.impl;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.other.dto.*;
import com.mdsd.cloud.controller.other.mapper.OtherMapper;
import com.mdsd.cloud.controller.other.service.OtherService;
import com.mdsd.cloud.controller.pc.emergencystation.mapper.MedPhepStationMapper;
import com.mdsd.cloud.entity.*;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.mapper.*;
import com.mdsd.cloud.utils.HandleString;
import com.mdsd.cloud.utils.ListUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-01-06]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OtherServiceImpl implements OtherService {

    @NonNull
    OtherMapper otherMapper;

    /**
     * 患者表
     */
    @NonNull
    MedPhepPatientsMapper medPhepPatientsMapper;

    /**
     * 任务表
     */
    @NonNull
    MedPhepMissionsMapper medPhepMissionsMapper;

    /**
     * 病历表
     */
    @NonNull
    MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;

    /**
     * 体格检查表
     */
    @NonNull
    MedPhepGeneralInspectionsMapper medPhepGeneralInspectionsMapper;

    /**
     * 交接单表
     */
    @NonNull
    MedPhepShiftRecordMapper medPhepShiftRecordMapper;

    /**
     * 用药表
     */
    @NonNull
    MedPhepEmergencyOrdersMapper medPhepEmergencyOrdersMapper;

    /**
     * 知情同意书表
     */
    @NonNull
    MedPhepConsentsMapper medPhepConsentsMapper;

    /**
     * 新冠肺炎流调表
     */
    @NonNull
    MedPhepEpidemiologicalMapper medPhepEpidemiologicalMapper;

    /**
     * 急救措施表
     */
    @NonNull
    MedPhepMeasuresMapper medPhepMeasuresMapper;

    /**
     * 数据字典
     */
    @NonNull
    DataDictionaryService dataDictionaryService;

    /**
     * 出车分站
     */
    @NonNull
    MedPhepStationMapper medPhepStationMapper;

    /**
     * 评分主表
     */
    @NonNull
    MedPhepEstimateMapper medPhepEstimateMapper;

    /**
     * 评分明细
     */
    @NonNull
    MedPhepEstimateItemMapper medPhepEstimateItemMapper;

    /**
     * 预警信息
     */
    @NonNull
    MedPhepNoticesMapper medPhepNoticesMapper;

    /**
     * Other - 急救病历
     */
    @Override
    public GetFirstAidMedicalRecordRso getFirstAidMedicalRecord(final GetFirstAidMedicalRecordPao getFirstAidMedicalRecordPo) {

        final GetFirstAidMedicalRecordRso result = new GetFirstAidMedicalRecordRso();
        //查询数据字典(PHFA_0011:疾病类别; PHFA_0035:健康码; PHFA_0056:通信行程码,PHFA_0032:病情转归)
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0011, DictionaryEnum.PHFA_0035, DictionaryEnum.PHFA_0056, DictionaryEnum.PHFA_0002, DictionaryEnum.PHFA_0033, DictionaryEnum.PHFA_0032);
        //查询患者基本信息
        Assert.notNull(getFirstAidMedicalRecordPo.getMissionId(),"MissionId为NULL");
        final MedPhepPatientsEntity medPhepPatientsEntity = this.medPhepPatientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery().eq(MedPhepPatientsEntity::getMissionId, getFirstAidMedicalRecordPo.getMissionId()));
        if (!Objects.isNull(medPhepPatientsEntity) && StringUtils.isNotBlank(medPhepPatientsEntity.getPatientId())) {
            //患者基本信息
            final PatientBaseInfoDto patientBaseInfoDto = new PatientBaseInfoDto();
            BeanUtils.copyProperties(medPhepPatientsEntity, patientBaseInfoDto);
            if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0002.getValue())) && null != patientBaseInfoDto.getAgeExtend()) {
                dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(dto -> patientBaseInfoDto.getAgeExtend().equals(dto.getItemcode())).findFirst().ifPresent(dto -> {
                    patientBaseInfoDto.setAgeExtend(dto.getItemname());
                });
            }
            result.setPatientBaseInfoDto(patientBaseInfoDto);
            //任务信息
            final MedPhepMissionsEntity medPhepMissionsEntity = this.medPhepMissionsMapper.selectById(getFirstAidMedicalRecordPo.getMissionId());
            if (!Objects.isNull(medPhepMissionsEntity)) {
                final TaskInfoDto taskInfoDto = new TaskInfoDto();
                BeanUtils.copyProperties(medPhepMissionsEntity, taskInfoDto);
                //处理出车分站
                if (null != taskInfoDto.getDispatchVehicleSite()) {
                    final MedPhepStationEntity medPhepStationEntity = this.medPhepStationMapper.selectById(taskInfoDto.getDispatchVehicleSite());
                    taskInfoDto.setDispatchVehicleSiteName(medPhepStationEntity.getName());
                }
                //添加预警时间
                final List<MedPhepNoticesEntity> medPhepNoticesEntities = this.medPhepNoticesMapper.selectList(Wrappers.<MedPhepNoticesEntity>lambdaQuery()
                        .eq(MedPhepNoticesEntity::getMissionId, getFirstAidMedicalRecordPo.getMissionId()));
                if(!CollectionUtils.isEmpty(medPhepNoticesEntities) &&  medPhepNoticesEntities.size()>0){
                    final Optional<MedPhepNoticesEntity> noticesEntityOptional = medPhepNoticesEntities.stream().max(Comparator.comparing(MedPhepNoticesEntity::getNoticeTime));
                    if(noticesEntityOptional.get().getNoticeTime() != null){
                        MedPhepNoticesEntity noticesEntity = noticesEntityOptional.get();
                        taskInfoDto.setNoticeTime(noticesEntity.getNoticeTime());
                    }
                }
                result.setTaskInfoDto(taskInfoDto);
            }
            //病历信息
            final MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity = this.medPhepMedicalRecordsMapper.selectOne(Wrappers.<MedPhepMedicalRecordsEntity>lambdaQuery().eq(MedPhepMedicalRecordsEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            if (!Objects.isNull(medPhepMedicalRecordsEntity)) {
                final MedicalRecordInfoDto medicalRecordInfoDto = new MedicalRecordInfoDto();
                BeanUtils.copyProperties(medPhepMedicalRecordsEntity, medicalRecordInfoDto);
                if (StringUtils.isNotBlank(medicalRecordInfoDto.getDiseaseCategory())) {
                    if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0011.getValue()))) {
                        dictByCode.get(DictionaryEnum.PHFA_0011.getValue()).stream().filter(ent -> medicalRecordInfoDto.getDiseaseCategory().equals(ent.getItemcode())).findFirst().ifPresent(ent -> {
                            medicalRecordInfoDto.setDiseaseCategory(ent.getItemname());
                            medicalRecordInfoDto.setCategory(ent.getExtend());
                        });
                    }
                }
                if (StringUtils.isNotBlank(medicalRecordInfoDto.getTreatmentResult())) {
                    if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0033.getValue()))) {
                        dictByCode.get(DictionaryEnum.PHFA_0033.getValue()).stream().filter(ent -> medicalRecordInfoDto.getTreatmentResult().equals(ent.getItemcode())).findFirst().ifPresent(ent -> {
                            medicalRecordInfoDto.setTreatmentResultName(ent.getItemname());
                        });
                    }
                }
                if (StringUtils.isNotBlank(medicalRecordInfoDto.getRescueChangeResult())) {
                    if (!CollectionUtils.isEmpty(dictByCode.get(DictionaryEnum.PHFA_0032.getValue()))) {
                        dictByCode.get(DictionaryEnum.PHFA_0032.getValue()).stream().filter(ent -> medicalRecordInfoDto.getRescueChangeResult().equals(ent.getItemcode())).findFirst().ifPresent(ent -> {
                            medicalRecordInfoDto.setRescueChangeResultName(ent.getItemname());
                        });
                    }
                }
                result.setMedicalRecordInfoDto(medicalRecordInfoDto);
            }
            //体格检查
            final List<MedPhepGeneralInspectionsEntity> medPhepGeneralInspectionsEntities = this.medPhepGeneralInspectionsMapper.selectList(Wrappers.<MedPhepGeneralInspectionsEntity>lambdaQuery().eq(MedPhepGeneralInspectionsEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            if (!CollectionUtils.isEmpty(medPhepGeneralInspectionsEntities)) {
                final List<PhysicalExaminationDto> physicalExaminationDtos = ListUtils.copyList(medPhepGeneralInspectionsEntities, PhysicalExaminationDto.class);
                result.setPhysicalExaminationDtoList(physicalExaminationDtos);
            }
            //辅助检查
            final SupplementaryExaminationDto supplementaryExaminationDto = new SupplementaryExaminationDto();
            if (!Objects.isNull(medPhepMedicalRecordsEntity)) {
                supplementaryExaminationDto.setBloodglucose(medPhepMedicalRecordsEntity.getBloodglucose());
                supplementaryExaminationDto.setOtherCheck(medPhepMedicalRecordsEntity.getOtherCheck());
            }
            final List<MedPhepPoctEntity> medPhepPoctEntities = new MedPhepPoctEntity().selectList(Wrappers.<MedPhepPoctEntity>lambdaQuery().eq(MedPhepPoctEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            if (!medPhepPoctEntities.isEmpty()) {
                final MedPhepPoctEntity medPhepPoctEntity = medPhepPoctEntities.stream().min(Comparator.comparing(MedPhepPoctEntity::getCheckTime)).get();
                supplementaryExaminationDto.setMedPhepPoctEntity(medPhepPoctEntity);
            }
            final List<MedPhepEstimateEntity> medPhepEstimateEntities = new MedPhepEstimateEntity().selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().eq(MedPhepEstimateEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            if (!CollectionUtils.isEmpty(medPhepEstimateEntities)) {
                final List<String> collect = medPhepEstimateEntities.stream().map(MedPhepEstimateEntity::getName).collect(Collectors.toList());
                if (collect.contains("ti")) {
                    final MedPhepEstimateEntity ti = medPhepEstimateEntities.stream().filter(s -> Objects.equals(s.getName(), "ti")).findFirst().orElse(null);
                    String tiScore = "";
                    if (ti.getValue().intValue() != 0) {
                        tiScore = ti.getValue().intValue() + "分-" + ti.getDescription();
                        supplementaryExaminationDto.setTiScore(tiScore);
                    } else {
                        supplementaryExaminationDto.setTiScore(ti.getDescription());
                    }
                }
                if (collect.contains("gcs")) {
                    final MedPhepEstimateEntity gcs = medPhepEstimateEntities.stream().filter(dto -> dto.getName().equals("gcs")).findFirst().orElse(null);
                    String gcsScore = "";
                    if (gcs.getValue().intValue() != 0) {
                        gcsScore = gcs.getValue().intValue() + "分-" + gcs.getDescription();
                        supplementaryExaminationDto.setGcsScore(gcsScore);
                    } else {
                        supplementaryExaminationDto.setGcsScore(gcs.getDescription());
                    }
                }
                if (collect.contains("chestPain")) {
                    final MedPhepEstimateEntity chestPain = medPhepEstimateEntities.stream().filter(dto -> dto.getName().equals("chestPain")).findFirst().orElse(null);
                    String chestScore = "";
                    if (chestPain.getValue().intValue() != 0) {
                        chestScore = chestPain.getValue().intValue() + "分-" + chestPain.getDescription();
                        supplementaryExaminationDto.setChestPainAssessment(chestScore);
                    } else {
                        supplementaryExaminationDto.setChestPainAssessment(chestPain.getDescription());
                    }
                }
                if (collect.contains("killip")) {
                    final MedPhepEstimateEntity killip = medPhepEstimateEntities.stream().filter(dto -> dto.getName().equals("killip")).findFirst().orElse(null);
                    String killipScore = "";
                    if (killip.getValue().intValue() != 0) {
                        killipScore = killip.getValue().intValue() + "分-" + killip.getDescription();
                        supplementaryExaminationDto.setKillip(killipScore);
                    } else {
                        supplementaryExaminationDto.setKillip(killip.getDescription());
                    }
                }
                if (collect.contains("fast")) {
                    final MedPhepEstimateEntity fast = medPhepEstimateEntities.stream().filter(dto -> dto.getName().equals("fast")).findFirst().orElse(null);
                    String fastScore = "";
                    if (fast.getValue().intValue() != 0) {
                        fastScore = fast.getValue().intValue() + "分-" + fast.getDescription();
                        supplementaryExaminationDto.setFastAssessment(fastScore);
                    } else {
                        supplementaryExaminationDto.setFastAssessment(fast.getDescription());
                    }
                }
            }
            //附件
            final List<MedPhepAttachmentEntity> medPhepAttachmentEntities = new MedPhepAttachmentEntity().selectList(Wrappers.<MedPhepAttachmentEntity>lambdaQuery().eq(MedPhepAttachmentEntity::getDeleteFlag, 0).eq(MedPhepAttachmentEntity::getBusinessId, medPhepPatientsEntity.getPatientId()));
            Optional.ofNullable(medPhepAttachmentEntities).filter(list -> list.size() > 0).ifPresent(list -> {
                //心电图
                supplementaryExaminationDto.setEcg(list.stream().filter(ent -> ent.getFileType().equals(0)).map(MedPhepAttachmentEntity::getRequestPath).collect(Collectors.toList()));
                //现场照片
                supplementaryExaminationDto.setSitePhotos(list.stream().filter(ent -> ent.getFileType().equals(1)).map(MedPhepAttachmentEntity::getRequestPath).collect(Collectors.toList()));
                //现场视频
                supplementaryExaminationDto.setLiveVideo(list.stream().filter(ent -> ent.getFileType().equals(2)).map(MedPhepAttachmentEntity::getRequestPath).collect(Collectors.toList()));
            });
            result.setSupplementaryExaminationDto(supplementaryExaminationDto);
            //诊断处理
            final MedPhepShiftRecordEntity medPhepShiftRecordEntity = this.medPhepShiftRecordMapper.selectOne(Wrappers.<MedPhepShiftRecordEntity>lambdaQuery().eq(MedPhepShiftRecordEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            if (!Objects.isNull(medPhepShiftRecordEntity)) {
                final DiagnosticProcessingDto diagnosticProcessingDto = new DiagnosticProcessingDto();
                BeanUtils.copyProperties(medPhepShiftRecordEntity, diagnosticProcessingDto);
                //查询用药信息处理
                final List<MedPhepEmergencyOrdersEntity> medPhepEmergencyOrdersEntities = this.medPhepEmergencyOrdersMapper.selectList(Wrappers.<MedPhepEmergencyOrdersEntity>lambdaQuery().eq(MedPhepEmergencyOrdersEntity::getPatientId, medPhepPatientsEntity.getPatientId())
                        .eq(MedPhepEmergencyOrdersEntity::getStatus, "1"));
                if (!CollectionUtils.isEmpty(medPhepEmergencyOrdersEntities)) {
                    List<MedPhepDrugEntity> medPhepDrugEntities = null;
                    final List<String> drugIds = medPhepEmergencyOrdersEntities.stream().filter(ent -> StringUtils.isNotBlank(ent.getDrugId())).map(MedPhepEmergencyOrdersEntity::getDrugId).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(drugIds)) {
                        //查询药品信息
                        medPhepDrugEntities = new MedPhepDrugEntity().selectList(Wrappers.<MedPhepDrugEntity>lambdaQuery().in(MedPhepDrugEntity::getId, drugIds));
                    }
                    final List<EmergencyOrdersDto> emergencyOrders = Lists.newArrayList();
                    for (final MedPhepEmergencyOrdersEntity entity : medPhepEmergencyOrdersEntities) {
                        final EmergencyOrdersDto emergencyOrdersDto = new EmergencyOrdersDto();
                        if (!CollectionUtils.isEmpty(medPhepDrugEntities)) {
                            medPhepDrugEntities.stream().filter(ent -> entity.getDrugId().equals(ent.getId())).findFirst().ifPresent(ent -> {
                                //药品信息
                                emergencyOrdersDto.setMedPhepDrugEntity(ent);
                                //专科对应药品类别
                                if (StringUtils.isNotBlank(ent.getSpecialistCategory())) {
                                    emergencyOrdersDto.setDrugCategoryList(JSON.parseArray(ent.getSpecialistCategory(), Map.class));
                                }
                            });
                        }
                        emergencyOrdersDto.setMedPhepEmergencyOrdersEntity(entity);
                        emergencyOrders.add(emergencyOrdersDto);
                    }
                    diagnosticProcessingDto.setEmergencyOrders(emergencyOrders);
                    diagnosticProcessingDto.setToHospital(medPhepShiftRecordEntity.getHandoverHospitalId());
                    if (!Objects.isNull(medPhepMedicalRecordsEntity)) {
                        diagnosticProcessingDto.setRescueChangeResult(medPhepMedicalRecordsEntity.getRescueChangeResult());
                    }
                }
                result.setDiagnosticProcessingDto(diagnosticProcessingDto);
            }
            //知情同意书
            final MedPhepConsentsEntity medPhepConsentsEntity = this.medPhepConsentsMapper.selectOne(Wrappers.<MedPhepConsentsEntity>lambdaQuery().eq(MedPhepConsentsEntity::getPatientId, medPhepPatientsEntity.getPatientId()).eq(MedPhepConsentsEntity::getStatus, 1));
            if (!Objects.isNull(medPhepConsentsEntity)) {
                result.setConsents(medPhepConsentsEntity.getIsInformed());
            }
            //新冠流调表
            final MedPhepEpidemiologicalEntity medPhepEpidemiologicalEntity = this.medPhepEpidemiologicalMapper.selectOne(Wrappers.<MedPhepEpidemiologicalEntity>lambdaQuery().eq(MedPhepEpidemiologicalEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            if (!Objects.isNull(medPhepEpidemiologicalEntity) && StringUtils.isNotBlank(medPhepEpidemiologicalEntity.getHealthCode()) && StringUtils.isNotBlank(medPhepEpidemiologicalEntity.getTourCode())) {
                //获取健康码和行程码枚举值
                final List<MedDictionaryEntity> phfa_0035 = dictByCode.get(DictionaryEnum.PHFA_0035.getValue());
                final List<MedDictionaryEntity> phfa_0056 = dictByCode.get(DictionaryEnum.PHFA_0056.getValue());
                final int healthCode = Integer.parseInt(medPhepEpidemiologicalEntity.getHealthCode());//健康码
                final int tourCode = Integer.parseInt(medPhepEpidemiologicalEntity.getTourCode());//行程码
                final String str = "%s-%s(%s)";
                if (healthCode > tourCode) {
                    //健康码值大于行程码值
                    phfa_0035.stream().filter(ent -> medPhepEpidemiologicalEntity.getHealthCode().equals(ent.getItemcode())).findFirst().ifPresent(ent -> {
                        result.setEpidemiological(String.format("%s %s", medPhepEpidemiologicalEntity.getCreateTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)),
                                String.format(str, "有异常-", "健康码", ent.getItemname())));
                    });
                } else if (healthCode < tourCode) {
                    //健康码值小于行程码值
                    phfa_0056.stream().filter(ent -> medPhepEpidemiologicalEntity.getTourCode().equals(ent.getItemcode())).findFirst().ifPresent(ent -> {
                        result.setEpidemiological(String.format("%s %s", medPhepEpidemiologicalEntity.getCreateTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)),
                                String.format(str, "有异常-", "通信行程码", ent.getItemname())));
                    });
                } else {
                    if (healthCode == 1) {
                        result.setEpidemiological(String.format("%s %s", medPhepEpidemiologicalEntity.getCreateTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)),
                                String.format(str, "无异常-", "健康码&通信行程码", phfa_0035.stream().filter(ent -> medPhepEpidemiologicalEntity.getHealthCode().equals(ent.getItemcode())).findFirst().get().getItemname())));
                    } else {
                        result.setEpidemiological(String.format("%s %s", medPhepEpidemiologicalEntity.getCreateTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)),
                                String.format(str, "有异常-", "健康码&通信行程码", phfa_0035.stream().filter(ent -> medPhepEpidemiologicalEntity.getHealthCode().equals(ent.getItemcode())).findFirst().get().getItemname())));
                    }
                }
            }
            //急救措施
            result.setMedPhepMeasuresEntityList(this.medPhepMeasuresMapper.selectList(Wrappers.<MedPhepMeasuresEntity>lambdaQuery().eq(MedPhepMeasuresEntity::getPatientId, medPhepPatientsEntity.getPatientId())));
        }
        return result;
    }

    /**
     * Other - 患者列表
     */
    @Override
    public IPage<GetPatientsPageRo> getPatientsPage(final GetPatientsPagePo getPatientsPagePo) {

        //处理模糊查询字段
        getPatientsPagePo.setName(HandleString.repString(getPatientsPagePo.getName()));
        final Page<GetPatientsPageRo> page = new Page<>(getPatientsPagePo.getCurrent(), getPatientsPagePo.getSize());
        return this.otherMapper.getPatientsPage(page, getPatientsPagePo);
    }

    /**
     * Other - 急救详情
     */
    @Override
    public FirstAidDetailsRo firstAidDetails(final FirstAidDetailsPo firstAidDetailsPo) {

        final FirstAidDetailsRo result = new FirstAidDetailsRo();
        final MedPhepPatientsEntity medPhepPatientsEntity = this.medPhepPatientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery().eq(MedPhepPatientsEntity::getMissionId, firstAidDetailsPo.getMissionId()));
        Optional.ofNullable(medPhepPatientsEntity).filter(ent -> null != ent.getPatientId()).ifPresent(ent -> {
            //查询数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0020, DictionaryEnum.PHFA_0021, DictionaryEnum.PHFA_0022, DictionaryEnum.PHFA_0023, DictionaryEnum.PHFA_0024, DictionaryEnum.PHFA_0025, DictionaryEnum.PHFA_0026, DictionaryEnum.PHFA_0027);
            result.setMedPhepPatientsEntity(ent);//患者信息
            //任务
            result.setMedPhepMissionsEntity(this.medPhepMissionsMapper.selectOne(Wrappers.<MedPhepMissionsEntity>lambdaQuery().eq(MedPhepMissionsEntity::getMissionId, firstAidDetailsPo.getMissionId())));
            //病历
            result.setMedPhepMedicalRecordsEntity(this.medPhepMedicalRecordsMapper.selectOne(Wrappers.<MedPhepMedicalRecordsEntity>lambdaQuery().eq(MedPhepMedicalRecordsEntity::getPatientId, ent.getPatientId())));
            //心电图
            final List<MedPhepAttachmentEntity> medPhepAttachmentEntities = new MedPhepAttachmentEntity().selectList(Wrappers.<MedPhepAttachmentEntity>lambdaQuery().eq(MedPhepAttachmentEntity::getDeleteFlag, 0).eq(MedPhepAttachmentEntity::getFileType, 0).eq(MedPhepAttachmentEntity::getBusinessId, ent.getPatientId()));
            if (!CollectionUtils.isEmpty(medPhepAttachmentEntities)) {
                result.setEcgList(medPhepAttachmentEntities.stream().map(MedPhepAttachmentEntity::getRequestPath).collect(Collectors.toList()));
            }
            //体格检查
            result.setGeneralList(new MedPhepGeneralInspectionsEntity().selectList(Wrappers.<MedPhepGeneralInspectionsEntity>lambdaQuery().eq(MedPhepGeneralInspectionsEntity::getPatientId, ent.getPatientId()).orderByAsc(MedPhepGeneralInspectionsEntity::getCreateTime)));
            //急救措施
            result.setMeasuresList(this.medPhepMeasuresMapper.selectList(Wrappers.<MedPhepMeasuresEntity>lambdaQuery().eq(MedPhepMeasuresEntity::getPatientId, ent.getPatientId())));
            //用药
            final List<MedPhepEmergencyOrdersEntity> medPhepEmergencyOrdersEntities = this.medPhepEmergencyOrdersMapper.selectList(Wrappers.<MedPhepEmergencyOrdersEntity>lambdaQuery().eq(MedPhepEmergencyOrdersEntity::getPatientId, ent.getPatientId()));
            Optional.ofNullable(medPhepEmergencyOrdersEntities).filter(list -> list.size() > 0).ifPresent(list -> {
                final List<String> collect = medPhepEmergencyOrdersEntities.stream().map(MedPhepEmergencyOrdersEntity::getDrugId).collect(Collectors.toList());
                final List<MedPhepDrugEntity> medPhepDrugEntities = new MedPhepDrugEntity().selectList(Wrappers.<MedPhepDrugEntity>lambdaQuery().eq(MedPhepDrugEntity::getDeleteFlag, 0).in(MedPhepDrugEntity::getId, collect));
                if (!CollectionUtils.isEmpty(medPhepDrugEntities)) {
                    final ArrayList<UsingDrugsDto> usingDrugs = Lists.newArrayList();
                    list.forEach(dto -> {
                        final UsingDrugsDto usingDrugsDto = new UsingDrugsDto();
                        final MedPhepDrugEntity medPhepDrugEntity = medPhepDrugEntities.stream().filter(mpd -> dto.getDrugId().equals(mpd.getId())).findFirst().get();
                        BeanUtils.copyProperties(medPhepDrugEntity, usingDrugsDto);
                        usingDrugsDto.setCreateTime(dto.getCreateTime());
                        usingDrugs.add(usingDrugsDto);
                    });
                    result.setUsingDrugsList(usingDrugs);
                }
            });
            //交接单
            result.setMedPhepShiftRecordEntity(this.medPhepShiftRecordMapper.selectOne(Wrappers.<MedPhepShiftRecordEntity>lambdaQuery().eq(MedPhepShiftRecordEntity::getPatientId, ent.getPatientId())));
            //POCT
            final MedPhepPoctEntity medPhepPoctEntity = new MedPhepPoctEntity().selectOne(Wrappers.<MedPhepPoctEntity>lambdaQuery().eq(MedPhepPoctEntity::getPatientId, ent.getPatientId()));
            Optional.ofNullable(medPhepPoctEntity).ifPresent(mpp -> {
                if (null != mpp.getTntUnit()) {
                    mpp.setTntUnit(dictByCode.get(DictionaryEnum.PHFA_0020.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getTntUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getTnlUnit()) {
                    mpp.setTnlUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getTnlUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getDdimerUnit()) {
                    mpp.setDdimerUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getDdimerUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getSerumUnit()) {
                    mpp.setSerumUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getSerumUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getCkmbUnit()) {
                    mpp.setCkmbUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getCkmbUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getBnpUnit()) {
                    mpp.setBnpUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getBnpUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getNtProbnpUnit()) {
                    mpp.setNtProbnpUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getNtProbnpUnit())).findFirst().get().getItemname());
                }
                if (null != mpp.getMyoUnit()) {
                    mpp.setMyoUnit(dictByCode.get(DictionaryEnum.PHFA_0021.getValue()).stream().filter(dto -> dto.getItemcode().equals(mpp.getMyoUnit())).findFirst().get().getItemname());
                }
                result.setMedPhepPoctEntity(mpp);
            });
            //评分
            result.setEstimateList(new MedPhepEstimateEntity().selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().eq(MedPhepEstimateEntity::getPatientId, ent.getPatientId())));
        });
        return result;
    }

    /**
     * Other - 患者列表 不分页
     */
    @Override
    public List<GetPatientsPageRo> getPatients(final GetPatientsPo getPatientsPo) {

        return this.otherMapper.getPatientsPage(getPatientsPo);
    }

    /**
     * Other - 通过患者ID获取医院ID、任务ID、病历ID
     */
    @Override
    public OneForFourRo oneForFour(final String patientId) {

        return this.otherMapper.oneForFour(patientId);
    }

    @Override
    public List<EstimateDto> getEstimateDetail(String missionId) {
        List<EstimateDto> estimateDtos = new ArrayList<>();
        final MedPhepPatientsEntity medPhepPatientsEntity = this.medPhepPatientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery().eq(MedPhepPatientsEntity::getMissionId, missionId));
        if (!Objects.isNull(medPhepPatientsEntity)) {
            final List<MedPhepEstimateEntity> medPhepEstimateEntities = this.medPhepEstimateMapper.selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().eq(MedPhepEstimateEntity::getPatientId, medPhepPatientsEntity.getPatientId()));
            medPhepEstimateEntities.forEach(item -> {
                EstimateDto estimateDto = new EstimateDto();
                BeanUtils.copyProperties(item, estimateDto);
                final List<MedPhepEstimateItemEntity> medPhepEstimateItemEntities = this.medPhepEstimateItemMapper.selectList(Wrappers.<MedPhepEstimateItemEntity>lambdaQuery().eq(MedPhepEstimateItemEntity::getEstimateId, item.getId()));
                final List<EstimateDetailDto> estimateDetailDtos = ListUtils.copyList(medPhepEstimateItemEntities, EstimateDetailDto.class);
                estimateDto.setEstimateDetails(estimateDetailDtos);
                estimateDtos.add(estimateDto);
            });
        }
        return estimateDtos;
    }
}
