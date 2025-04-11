package com.mdsd.cloud.controller.registration.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.registration.dto.*;
import com.mdsd.cloud.controller.registration.entity.*;
import com.mdsd.cloud.controller.registration.function.MedClinicVitalsignsFunction;
import com.mdsd.cloud.controller.registration.function.MedClinicVulnusDegreeFunction;
import com.mdsd.cloud.controller.registration.function.MedClinicVulnusDetailFunction;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCardInfoMapper;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCheckMasterMapper;
import com.mdsd.cloud.controller.registration.mapper.MedClinicModifyreasonMapper;
import com.mdsd.cloud.controller.registration.service.RegistrationInfoService;
import com.mdsd.cloud.controller.registration.struct.EntityMapStruct;
import com.mdsd.cloud.controller.triage.mapper.MedClinicTriageHelpMapper;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.feign.DasServerFeign;
import com.mdsd.cloud.feign.dto.DasNotificationPo;
import com.mdsd.cloud.response.exception.BusinessException;
import com.mdsd.cloud.utils.CommonUtils;
import com.mdsd.cloud.utils.HandleString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2021-09-13]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationInfoServiceImpl implements RegistrationInfoService {

    @NonNull
    MedClinicCardInfoMapper medClinicCardInfoMapper;

    @NonNull
    MedClinicCheckMasterMapper medClinicCheckMasterMapper;

    @NonNull
    MedClinicVitalsignsFunction medClinicVitalsignsFunction;

    @NonNull
    MedClinicVulnusDegreeFunction medClinicVulnusDegreeFunction;

    @NonNull
    MedClinicVulnusDetailFunction medClinicVulnusDetailFunction;

    @NonNull
    MedClinicTriageHelpMapper medClinicTriageHelpMapper;

    @NonNull
    MedClinicModifyreasonMapper medClinicModifyreasonMapper;

    @NonNull
    EntityMapStruct entityMapStruct;

    @NonNull
    ApplicationContext applicationContext;

    @NonNull
    DasServerFeign dasServerFeign;

    @NonNull
    DataDictionaryService dataDictionaryService;

    /**
     * 个人登记 - 新增or更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean regSaveOrUpdate(final RegSaveOrUpdateParamDto regSaveOrUpdateParamDto) {

        boolean result = false;
        //判断入参数据是否存在
        if (Objects.nonNull(regSaveOrUpdateParamDto)) {
            if (StringUtils.isNotBlank(regSaveOrUpdateParamDto.getRegId())) {
                final MedClinicCheckMasterEntity medClinicCheckMasterEntity = this.entityMapStruct.toMedClinicCheckMasterEntity(regSaveOrUpdateParamDto);
                if (Objects.nonNull(medClinicCheckMasterEntity)) {
                    //判断就诊数据是否存在
                    final MedClinicCheckMasterEntity checkMasterEntity = this.medClinicCheckMasterMapper.selectById(regSaveOrUpdateParamDto.getRegId());
                    //1.处理患者基本信息数据
                    MedClinicCardInfoEntity cardInfoEntity = null;
                    if (Objects.nonNull(checkMasterEntity)) {
                        cardInfoEntity =
                                this.medClinicCardInfoMapper.selectOne(Wrappers.<MedClinicCardInfoEntity>lambdaQuery().eq(MedClinicCardInfoEntity::getInfoId, checkMasterEntity.getInfoId()));
                    }
                    //入参数据映射到 MedClinicCardInfoEntity
                    final MedClinicCardInfoEntity entity = this.entityMapStruct.toMedClinicCardInfoEntity(regSaveOrUpdateParamDto);
                    if (!ObjectUtils.isEmpty(entity)) {
                        if (Objects.nonNull(cardInfoEntity)) {
                            //患者数据存在,执行更新
                            if (StringUtils.isNotBlank(entity.getInfoId())) {
                                result = this.medClinicCardInfoMapper.updateById(entity) > CommonConstants.ZERO;
                            }
                        } else {
                            //患者数据不存在,执行新增(注: 三无患者全部是新增操作)
                            result = this.medClinicCardInfoMapper.insert(entity) > CommonConstants.ZERO;
                        }
                    }
                    medClinicCheckMasterEntity.setInfoId(entity.getInfoId());
                    //2.处理就诊信息数据
                    if (Objects.nonNull(checkMasterEntity)) {
                        //就诊数据存在,执行更新
                        result = this.medClinicCheckMasterMapper.updateById(medClinicCheckMasterEntity) > CommonConstants.ZERO;
                    } else {
                        //就诊数据不存在,执行新增
                        medClinicCheckMasterEntity.setIsUsed(CommonConstants.ONE_STR);
                        result = this.medClinicCheckMasterMapper.insert(medClinicCheckMasterEntity) > CommonConstants.ZERO;
                    }
                }
                //3.处理生命体征数据
                if (Objects.nonNull(regSaveOrUpdateParamDto.getDatas()) && Objects.nonNull(regSaveOrUpdateParamDto.getDatas().getVitalSigns())) {
                    //入参数据映射到: MedClinicVitalsignsEntity
                    final MedClinicVitalsignsEntity medClinicVitalsignsEntity = this.entityMapStruct.toMedClinicVitalsignsEntity(regSaveOrUpdateParamDto.getDatas().getVitalSigns());
                    if (Objects.nonNull(medClinicVitalsignsEntity)) {
                        medClinicVitalsignsEntity.setRegId(medClinicCheckMasterEntity.getRegId());
                        result = this.medClinicVitalsignsFunction.save(medClinicVitalsignsEntity);
                    }
                }
                //4.处理评分数据
                if (!CollectionUtils.isEmpty(regSaveOrUpdateParamDto.getScores())) {
                    final List<MedClinicVulnusDegreeEntity> degreeEntityList = Lists.newArrayList();
                    final List<MedClinicVulnusDetailEntity> detailEntityList = Lists.newArrayList();
                    regSaveOrUpdateParamDto.getScores().forEach(scoreDto -> {
                        //入参数据映射到: MedClinicVulnusDegreeEntity
                        final MedClinicVulnusDegreeEntity medClinicVulnusDegreeEntity = new MedClinicVulnusDegreeEntity();
                        BeanUtils.copyProperties(scoreDto, medClinicVulnusDegreeEntity);
                        medClinicVulnusDegreeEntity.setRegId(medClinicCheckMasterEntity.getRegId());
                        medClinicVulnusDegreeEntity.setVdId(CommonUtils.GetRandomUUID());
                        medClinicVulnusDegreeEntity.setDeleteSign(0);
                        degreeEntityList.add(medClinicVulnusDegreeEntity);
                        //入参数据映射到: MedClinicVulnusDetailEntity
                        if (!CollectionUtils.isEmpty(scoreDto.getDetails())) {
                            scoreDto.getDetails().forEach(detailDto -> {
                                final MedClinicVulnusDetailEntity medClinicVulnusDetailEntity = new MedClinicVulnusDetailEntity();
                                BeanUtils.copyProperties(detailDto, medClinicVulnusDetailEntity);
                                medClinicVulnusDetailEntity.setDetailId(CommonUtils.GetRandomUUID());
                                medClinicVulnusDetailEntity.setVdId(medClinicVulnusDegreeEntity.getVdId());
                                medClinicVulnusDetailEntity.setContent(JSON.toJSONString(detailDto));
                                detailEntityList.add(medClinicVulnusDetailEntity);
                            });
                        }
                    });
                    if (!CollectionUtils.isEmpty(degreeEntityList)) {
                        result = this.medClinicVulnusDegreeFunction.saveBatch(degreeEntityList);
                        if (!CollectionUtils.isEmpty(detailEntityList)) {
                            result = this.medClinicVulnusDetailFunction.saveBatch(detailEntityList);
                        }
                    }
                }
                // 推送数据
                final DasNotificationPo dasNotificationPo = new DasNotificationPo().setHisNo(regSaveOrUpdateParamDto.getRegId()).setHospitalId(regSaveOrUpdateParamDto.getHospitalId()).setUserId(regSaveOrUpdateParamDto.getCreateId()).setHospitalId(regSaveOrUpdateParamDto.getHospitalArer());
                log.info("DAS - 确认分诊推送数据_入参 -> {}", JSON.toJSONString(dasNotificationPo));
                this.dasServerFeign.dasNotification(dasNotificationPo);
            }
        }
        return result;
    }

    /**
     * 个人登记 - 删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean regRemove(final List<String> ids) {

        boolean result = false;
        if (!CollectionUtils.isEmpty(ids)) {
            result = new MedClinicCheckMasterEntity().update(Wrappers.<MedClinicCheckMasterEntity>lambdaUpdate().set(MedClinicCheckMasterEntity::getIsUsed, CommonConstants.ZERO_STR).in(MedClinicCheckMasterEntity::getRegId, ids));
        }
        return result;
    }

    /**
     * 个人登记 - 详情
     */
    @Override
    public RegDetailsResultDto regDetails(final String regId) {

        final RegDetailsResultDto result = new RegDetailsResultDto();

        final MedClinicCheckMasterEntity checkMasterEntity = this.medClinicCheckMasterMapper.selectById(regId);
        Optional.ofNullable(checkMasterEntity).filter(dto -> !ObjectUtils.isEmpty(dto)).ifPresent(dto -> {
            //处理基本信息
            BeanUtils.copyProperties(checkMasterEntity, result);
            if (StringUtils.isNotBlank(checkMasterEntity.getInfoId())) {
                final MedClinicCardInfoEntity cardInfoEntity =
                        this.medClinicCardInfoMapper.selectById(checkMasterEntity.getInfoId());
                BeanUtils.copyProperties(cardInfoEntity, result);
            }
            //处理生命体征、到院信息、流行病学史
            final MedClinicVitalsignsEntity medClinicVitalsignsEntity =
                    this.medClinicVitalsignsFunction.getOne(Wrappers.<MedClinicVitalsignsEntity>lambdaQuery().eq(MedClinicVitalsignsEntity::getRegId, checkMasterEntity.getRegId()).orderByDesc(MedClinicVitalsignsEntity::getAcqTime), false);
            Optional.ofNullable(medClinicVitalsignsEntity).filter(vitalsignsEntity -> !ObjectUtils.isEmpty(vitalsignsEntity)).ifPresent(vitalsignsEntity -> {
                final VitalSignsDto vitalSignsDto = new VitalSignsDto();
                BeanUtils.copyProperties(vitalsignsEntity, vitalSignsDto);
                final ArriveInfoDto arriveInfoDto = new ArriveInfoDto();
                BeanUtils.copyProperties(checkMasterEntity, arriveInfoDto);
                final InfectHistoryDto infectHistoryDto = new InfectHistoryDto();
                BeanUtils.copyProperties(checkMasterEntity, infectHistoryDto);
                final DatasDto datasDto = new DatasDto();
                datasDto.setVitalSigns(vitalSignsDto);
                datasDto.setArriveInfo(arriveInfoDto);
                datasDto.setInfectHistory(infectHistoryDto);
                result.setDatas(datasDto);
            });
            //处理患者评分
            final List<MedClinicVulnusDegreeEntity> degreeEntityList =
                    this.medClinicVulnusDegreeFunction.list(Wrappers.<MedClinicVulnusDegreeEntity>lambdaQuery().eq(MedClinicVulnusDegreeEntity::getRegId, checkMasterEntity.getRegId()));
            Optional.ofNullable(degreeEntityList).filter(list -> list.size() > CommonConstants.ZERO).ifPresent(list -> {
                final ArrayList<ScoreDto> objects = Lists.newArrayList();
                final List<String> vdIdList = list.stream().map(MedClinicVulnusDegreeEntity::getVdId).collect(Collectors.toList());
                final List<MedClinicVulnusDetailEntity> detailEntityList =
                        this.medClinicVulnusDetailFunction.list(Wrappers.<MedClinicVulnusDetailEntity>lambdaQuery().in(MedClinicVulnusDetailEntity::getVdId, vdIdList));
                list.forEach(degreeDto -> {
                    final ScoreDto scoreDto = new ScoreDto();
                    final List<MedClinicVulnusDetailEntity> collect =
                            detailEntityList.stream().filter(detailEntity -> degreeDto.getVdId().equals(detailEntity.getVdId())).collect(Collectors.toList());
                    final List<Object> detailDtoList = Lists.newArrayList();
                    collect.forEach(detailEntity -> {
                        detailDtoList.add(detailEntity);
                    });
                    BeanUtils.copyProperties(degreeDto, scoreDto);
                    scoreDto.setDetails(detailDtoList);
                    objects.add(scoreDto);
                });
                result.setScores(objects);
            });
            //处理分级
            final GradeDto gradeDto = new GradeDto();
            BeanUtils.copyProperties(checkMasterEntity, gradeDto);
            result.setGrade(gradeDto);
            //处理患者去向
            final WhereaboutsDto whereaboutsDto = new WhereaboutsDto();
            BeanUtils.copyProperties(checkMasterEntity, whereaboutsDto);
            result.setWhereabouts(whereaboutsDto);
        });
        return result;
    }

    /**
     * 个人登记 - 列表
     */
    @Override
    public Map<String, Object> regList(final RegListsParamDto regListsParamDto) {

        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0014.getValue(), DictionaryEnum.PIAT_0008.getValue(), DictionaryEnum.PIAT_0015.getValue(), DictionaryEnum.PIAT_0004.getValue(), DictionaryEnum.PIAT_0007.getValue());
        //查询知识库主诉
        final List<GetPatientSayList> patientSayList = this.medClinicCardInfoMapper.getPatientSayList();
        //处理分诊时间
        Optional.ofNullable(regListsParamDto.getTriageTime()).filter(list -> list.size() > 0).ifPresent(list -> {
            regListsParamDto.setTriageTimeStart(LocalDateTime.parse(list.get(0), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            regListsParamDto.setTriageTimeEnd(LocalDateTime.parse(list.get(1), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        });
        //处理模糊查询字段
        regListsParamDto.setSearchValue(HandleString.repString(regListsParamDto.getSearchValue()));
        final Page<RegListsResultDto> page = new Page<>(regListsParamDto.getCurrent(), regListsParamDto.getPageSize());
        final IPage<RegListsResultDto> regListResultDtoIPage = this.medClinicCardInfoMapper.regList(regListsParamDto, page);
        Optional.ofNullable(regListResultDtoIPage.getRecords()).filter(list -> list.size() > CommonConstants.ZERO && dictByCode.size() > CommonConstants.ZERO).ifPresent(list -> {
            list.forEach(dto -> {
                //分诊级别
                List<MedDictionaryEntity> drDtoList = dictByCode.get(DictionaryEnum.PIAT_0009.getValue());
                if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(dto.getResetLevel())) {
                    for (final MedDictionaryEntity drDto : drDtoList) {
                        if (dto.getResetLevel().equals(drDto.getItemcode())) {
                            dto.setResetLevel(drDto.getItemname());
                        }
                    }
                }
                //患者主诉
                if (StringUtils.isNotBlank(dto.getSubjective())) {
                    final GetPatientSayList getPatientSayList = patientSayList.stream().filter(psDto -> psDto.getPatientSayid().equals(dto.getSubjective())).findFirst().get();
                    dto.setSubjective(getPatientSayList.getDescription());
                }
                //生命体征
                if (dto.getMcvInfo().length() <= CommonConstants.FIVE) {
                    dto.setMcvInfo("--");
                }
                //就诊科室
                drDtoList = dictByCode.get(DictionaryEnum.PIAT_0014.getValue());
                if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(dto.getDepartmentId())) {
                    for (final MedDictionaryEntity drDto : drDtoList) {
                        if (dto.getDepartmentId().equals(drDto.getItemcode())) {
                            dto.setDepartmentId(drDto.getItemname());
                        }
                    }
                }
                //去向
                drDtoList = dictByCode.get(DictionaryEnum.PIAT_0008.getValue());
                if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(dto.getTurnResult())) {
                    for (final MedDictionaryEntity drDto : drDtoList) {
                        if (dto.getTurnResult().equals(drDto.getItemcode())) {
                            dto.setTurnResult(drDto.getItemname());
                        }
                    }
                }
                //就诊医师
                drDtoList = dictByCode.get(DictionaryEnum.PIAT_0015.getValue());
                if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(dto.getCheckDoctorId())) {
                    for (final MedDictionaryEntity drDto : drDtoList) {
                        if (dto.getCheckDoctorId().equals(drDto.getItemcode())) {
                            dto.setCheckDoctorId(drDto.getItemname());
                        }
                    }
                }
                //来院方式
                drDtoList = dictByCode.get(DictionaryEnum.PIAT_0004.getValue());
                if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(dto.getArriveType())) {
                    for (final MedDictionaryEntity drDto : drDtoList) {
                        if (dto.getArriveType().equals(drDto.getItemcode())) {
                            dto.setArriveType(drDto.getItemname());
                        }
                    }
                }
                //意识
                drDtoList = dictByCode.get(DictionaryEnum.PIAT_0007.getValue());
                if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(dto.getMind())) {
                    for (final MedDictionaryEntity drDto : drDtoList) {
                        if (dto.getMind().equals(drDto.getItemcode())) {
                            dto.setMind(drDto.getItemname());
                        }
                    }
                }
            });
            regListResultDtoIPage.setRecords(list);
        });
        //处理Result Massage
        final Map<String, Long> resultMassage = Maps.newHashMap();
        resultMassage.put("分诊总人数", regListResultDtoIPage.getTotal());
        resultMassage.put("一级患者", CommonConstants.ZERO_LONG);
        resultMassage.put("二级患者", CommonConstants.ZERO_LONG);
        resultMassage.put("三级患者", CommonConstants.ZERO_LONG);
        resultMassage.put("四级患者", CommonConstants.ZERO_LONG);
        final Page<RegListsResultDto> pageAll = new Page<>(0, regListResultDtoIPage.getTotal());
        final IPage<RegListsResultDto> regListResultDtoAll = this.medClinicCardInfoMapper.regList(regListsParamDto, pageAll);
        Optional.ofNullable(regListResultDtoAll.getRecords()).filter(list -> list.size() > CommonConstants.ZERO).ifPresent(list -> {
            final List<RegListsResultDto> collect = list.stream().filter(dto -> StringUtils.isNotBlank(dto.getResetLevel())).collect(Collectors.toList());
            resultMassage.put("一级患者",
                    collect.stream().map(RegListsResultDto::getResetLevel).filter(reselevel -> reselevel.equals("01")).count());
            resultMassage.put("二级患者",
                    collect.stream().map(RegListsResultDto::getResetLevel).filter(reselevel -> reselevel.equals("02")).count());
            resultMassage.put("三级患者",
                    collect.stream().map(RegListsResultDto::getResetLevel).filter(reselevel -> reselevel.equals("03")).count());
            resultMassage.put("四级患者",
                    collect.stream().map(RegListsResultDto::getResetLevel).filter(reselevel -> reselevel.equals("04")).count());
        });
        final Map<String, Object> result = Maps.newHashMap();
        result.put("iPage", regListResultDtoIPage);
        result.put("resultMassage", resultMassage);
        return result;
    }

    /**
     * 个人登记 - 列表_导出
     */
    @Override
    public void regExport(final RegListsParamDto regListsParamDto, final HttpServletResponse response) {

        //查询数据
        final Map<String, Object> map = this.regList(regListsParamDto);
        if (map.size() > CommonConstants.ONE) {
            final IPage<RegListsResultDto> iPage = (IPage<RegListsResultDto>) map.get("iPage");
            final List<RegListsResultDto> records = iPage.getRecords();
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                final String fileName = URLEncoder.encode("急诊患者预检分诊登记表.xlsx", "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                EasyExcel.write(response.getOutputStream()).withTemplate(RegistrationInfoServiceImpl.class.getClassLoader().getResourceAsStream("excel-templates/regExportExcel.xlsx")).sheet().doFill(records);
            } catch (final IOException e) {
                throw new BusinessException("Excel导出失败");
            }
        }
    }

    /**
     * 个人登记 - 删除_患者评分历史记录
     */
    @Override
    public boolean scoRemove(final List<String> ids) {

        return new MedClinicVulnusDegreeEntity().update(Wrappers.<MedClinicVulnusDegreeEntity>lambdaUpdate().set(MedClinicVulnusDegreeEntity::getDeleteSign, 1).in(MedClinicVulnusDegreeEntity::getVdId, ids));
    }

    /**
     * 个人登记 - 列表_评分历史记录
     */
    @Override
    public List<MedClinicVulnusDegreeEntity> getScoreList(final String regId, final String vulnusType) {

        return this.medClinicVulnusDegreeFunction.list(Wrappers.<MedClinicVulnusDegreeEntity>lambdaQuery().eq(MedClinicVulnusDegreeEntity::getDeleteSign, 0).eq(MedClinicVulnusDegreeEntity::getRegId, regId).eq(MedClinicVulnusDegreeEntity::getVulnusType, vulnusType));
    }

    /**
     * 个人登记 - 详情_患者评分历史记录
     */
    @Override
    public List<Object> getScoreDetails(final String vdId) {

        final List<Object> result = Lists.newArrayList();
        final List<MedClinicVulnusDetailEntity> list = this.medClinicVulnusDetailFunction.list(Wrappers.<MedClinicVulnusDetailEntity>lambdaQuery().eq(MedClinicVulnusDetailEntity::getVdId, vdId));
        Optional.ofNullable(list).filter(ent -> ent.size() > CommonConstants.ZERO).ifPresent(ent -> {
            ent.forEach(dto -> {
                if (dto.getContent().contains("GCS评分")) {
                    final GcsDto gcsDto = JSON.parseObject(dto.getContent(), GcsDto.class);
                    result.add(gcsDto);
                } else {
                    final GetScoreDetailsResultDto getScoreDetailsResultDto = JSON.parseObject(dto.getContent(),
                            GetScoreDetailsResultDto.class);
                    result.add(getScoreDetailsResultDto);
                }
            });
        });
        return result;
    }

    /**
     * 个人登记 - 新增or更新_修改分诊分级原因
     */
    @Override
    public boolean modSaveOrUpdate(final ModSaveOrUpdateParamDto modSaveOrUpdateParamDto) {

        boolean result = false;
        if (Objects.nonNull(modSaveOrUpdateParamDto)) {
            final MedClinicModifyreasonEntity medClinicModifyreasonEntity = this.entityMapStruct.toMedClinicModifyreasonEntity(modSaveOrUpdateParamDto);
            result = this.medClinicModifyreasonMapper.insert(medClinicModifyreasonEntity) > CommonConstants.ZERO;
        }
        return result;
    }

    /**
     * 个人登记 - 急诊明细
     */
    @Override
    public void TriageDetailed(final TriageDetailedParamDto triageDetailedParamDto) {

        //查询患者分诊信息
        final MedClinicCheckMasterEntity medClinicCheckMasterEntity = this.medClinicCheckMasterMapper.selectById(triageDetailedParamDto.getRegId());
        if (!ObjectUtils.isEmpty(medClinicCheckMasterEntity)) {
            //查询患者个人信息
            final MedClinicCardInfoEntity entity = this.medClinicCardInfoMapper.selectById(medClinicCheckMasterEntity.getInfoId());
            if (!ObjectUtils.isEmpty(entity)) {
                final HttpServletResponse response = triageDetailedParamDto.getResponse();
                try {
                    // 设置响应头，控制浏览器下载该文件
                    triageDetailedParamDto.getResponse().setHeader("content-disposition", String.format("attachment;filename=%s", URLEncoder.encode("急诊明细.pdf", "UTF-8")));
                    final PDDocument document = new PDDocument();//创建文档
                    final PDPage pdPage = new PDPage(PDRectangle.A4);//创建一个空白页,并设置纸张大小
                    document.addPage(pdPage);//将空白页添加到文档中
                    final PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
                    final PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流

                    String title = "麦迪市第一人民医院";
                    Map<String, Float> map = this.centerCoordinate(pdPage, font, 20F, 30F, title);
                    this.ContentStream(pdPageContentStream, font, 20F, RenderingMode.STROKE, 20F, map.get("x"), map.get("y"), title);
                    title = "急诊病人诊疗流程记录单";
                    map = this.centerCoordinate(pdPage, font, 20F, 60F, title);
                    this.ContentStream(pdPageContentStream, null, null, null, null, map.get("x"), map.get("y"), title);

                    final Float width = 30F;
                    Float height = pdPage.getMediaBox().getHeight() - 110;
                    this.ContentStream(pdPageContentStream, font, 12F, RenderingMode.FILL, null, width, height, "姓名:__________________");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 172F, height, "性别:_____");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 235F, height, "年龄:_____");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 299F, height, "病历号:_____________");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 422F, height, "急诊编号:_______________");
                    //业务数据
                    height = height + 1;
                    this.ContentStream(pdPageContentStream, font, 11F, null, null, 61F, height, entity.getName());
                    this.ContentStream(pdPageContentStream, null, null, null, null, 204F, height, CommonConstants.STR_ZERO_ONE.equals(entity.getGender()) ? "男" : CommonConstants.STR_ZERO_TWO.equals(entity.getGender()) ? "女" : "未知");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 267F, height, String.valueOf(medClinicCheckMasterEntity.getRegAge()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 341F, height, entity.getPatientId());
                    this.ContentStream(pdPageContentStream, null, null, null, null, 478F, height, medClinicCheckMasterEntity.getRegId());

                    height = pdPage.getMediaBox().getHeight() - 135;
                    this.ContentStream(pdPageContentStream, font, 12F, null, null, width, height, "日期:__________________");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 172F, height, "床号:_____");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 235F, height, "诊断:__________________________________________________");
                    //业务数据
                    height = height + 1;
                    this.ContentStream(pdPageContentStream, font, 11F, null, null, 61F, height, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
                    this.ContentStream(pdPageContentStream, font, 11F, null, null, 204F, height, "抢-7");
                    this.ContentStream(pdPageContentStream, font, 11F, null, null, 267F, height, medClinicCheckMasterEntity.getDiagnoseDescribe());

                    //表格
                    height = pdPage.getMediaBox().getHeight() - 145;
                    List<String> list = new ArrayList<String>() {{
                        this.add("时间");
                        this.add("诊疗内容");
                        this.add("操作员工");
                    }};
                    this.frameLine(pdPageContentStream, font, height, list, 0);

                    list = new ArrayList<String>() {{
                        this.add("分诊");
                        this.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(medClinicCheckMasterEntity.getTriageTime()));
                        this.add("分诊登记");
                        this.add("刘敏");
                    }};
                    this.frameLine(pdPageContentStream, font, height - 25, list, 1);

                    pdPageContentStream.close();//关闭流
                    document.save(triageDetailedParamDto.getResponse().getOutputStream());//将文档保存到输出流中
                    document.close();//关闭文档
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 个人登记 - 分诊凭条
     */
    @Override
    public void triageSlip(final TriageSlipParamDto triageSlipParamDto) {

        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0014.getValue());
        //查询患者分诊信息
        final MedClinicCheckMasterEntity medClinicCheckMasterEntity = this.medClinicCheckMasterMapper.selectById(triageSlipParamDto.getRegId());
        if (!ObjectUtils.isEmpty(medClinicCheckMasterEntity)) {
            //分诊级别
            List<MedDictionaryEntity> drDtoList = dictByCode.get(DictionaryEnum.PIAT_0009.getValue());
            if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(medClinicCheckMasterEntity.getResetLevel())) {
                for (final MedDictionaryEntity drDto : drDtoList) {
                    if (medClinicCheckMasterEntity.getResetLevel().equals(drDto.getItemcode())) {
                        medClinicCheckMasterEntity.setResetLevel(drDto.getItemname());
                    }
                }
            }
            //就诊科室
            drDtoList = dictByCode.get(DictionaryEnum.PIAT_0014.getValue());
            if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(medClinicCheckMasterEntity.getDepartmentId())) {

                for (final MedDictionaryEntity drDto : drDtoList) {
                    if (medClinicCheckMasterEntity.getDepartmentId().equals(drDto.getItemcode())) {
                        medClinicCheckMasterEntity.setDepartmentId(drDto.getItemname());
                    }
                }
            }
            //查询患者生命体征
            MedClinicVitalsignsEntity medClinicVitalsignsEntity = null;
            final List<MedClinicVitalsignsEntity> list = this.medClinicVitalsignsFunction.list(Wrappers.<MedClinicVitalsignsEntity>lambdaQuery().eq(MedClinicVitalsignsEntity::getRegId, medClinicCheckMasterEntity.getRegId()).orderByDesc(MedClinicVitalsignsEntity::getAcqTime));
            if (!CollectionUtils.isEmpty(list)) {
                medClinicVitalsignsEntity = list.get(CommonConstants.ZERO);
            }
            //查询患者个人信息
            final MedClinicCardInfoEntity entity = this.medClinicCardInfoMapper.selectById(medClinicCheckMasterEntity.getInfoId());
            if (!ObjectUtils.isEmpty(entity)) {
                try {
                    // 设置响应头，控制浏览器下载该文件
                    triageSlipParamDto.getResponse().setHeader("content-disposition", "attachment;filename="
                            + URLEncoder.encode("分诊凭条.pdf", "UTF-8"));
                    final PDDocument document = new PDDocument();//创建文档
                    final PDPage pdPage = new PDPage(PDRectangle.A6);//创建一个空白页,并设置纸张大小
                    document.addPage(pdPage);//将空白页添加到文档中

                    final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("font/simsun.ttf");
                    final PDType0Font font = PDType0Font.load(document, resourceAsStream);
                    final PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流

                    this.ContentStream(pdPageContentStream, font, 16F, RenderingMode.FILL_STROKE, 20F, 75F, 375F, "xx省人民医院");
                    this.ContentStream(pdPageContentStream, font, 12F, null, null, 175F, 375F, String.format("急  %s", medClinicCheckMasterEntity.getResetLevel()));
                    this.ContentStream(pdPageContentStream, null, null, RenderingMode.FILL, null, 25F, 350F, String.format("姓名:%s", entity.getName()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 127F, 350F, String.format("性别:%s", CommonConstants.STR_ZERO_ONE.equals(entity.getGender()) ? "男" : CommonConstants.STR_ZERO_TWO.equals(entity.getGender()) ? "女" : "未知"));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 193F, 350F, String.format("年龄:%s岁", medClinicCheckMasterEntity.getRegAge()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 330F, "-----------------------------------------");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 310F, "T: ______ ℃");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 47F, 310F, !ObjectUtils.isEmpty(medClinicVitalsignsEntity) && medClinicVitalsignsEntity.getTemperature() != null ? medClinicVitalsignsEntity.getTemperature().toString() : "--");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 143F, 310F, "P: ______ 次/分");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 165F, 310F, !ObjectUtils.isEmpty(medClinicVitalsignsEntity) && medClinicVitalsignsEntity.getPlus() != null ? medClinicVitalsignsEntity.getPlus().toString() : "--");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 290F, "R: ______ 次/分");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 47F, 290F, !ObjectUtils.isEmpty(medClinicVitalsignsEntity) && medClinicVitalsignsEntity.getBreath() != null ? medClinicVitalsignsEntity.getBreath().toString() : "--");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 143F, 290F, "BP: ____________ mmHg");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 170F, 290F, String.format("%s/%s", !ObjectUtils.isEmpty(medClinicVitalsignsEntity) && medClinicVitalsignsEntity.getSbp() != null ? medClinicVitalsignsEntity.getSbp().toString() : "--", !ObjectUtils.isEmpty(medClinicVitalsignsEntity) && medClinicVitalsignsEntity.getDbp() != null ? medClinicVitalsignsEntity.getDbp().toString() : "--"));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 270F, "SPO2: ______ %");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 65F, 270F, !ObjectUtils.isEmpty(medClinicVitalsignsEntity) && medClinicVitalsignsEntity.getSpo() != null ? medClinicVitalsignsEntity.getSpo().toString() : "--");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 250F, "-----------------------------------------");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 230F, String.format("就诊科室:%s", medClinicCheckMasterEntity.getDepartmentId()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 210F, String.format("分诊时间:%s", medClinicCheckMasterEntity.getTriageTime().format(DateTimeFormatter.ofPattern(
                            CommonConstants.DATE_TIME))));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 25F, 190F, "-----------------------------------------");
                    this.ContentStream(pdPageContentStream, font, 14F, RenderingMode.FILL_STROKE, null, 25F, 170F, "请妥善保管此单据，用于就诊，挂号 !");

                    //生成条形码
                    final Code128Writer writer = new Code128Writer();
                    final String contents = medClinicCheckMasterEntity.getRegId().trim();
                    final BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.CODE_128, 50, 25);
                    final String filePath = String.format("\\%s.png", contents);
                    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
                    pdPageContentStream.drawImage(PDImageXObject.createFromFile(filePath, document), 90F, 130F);

                    pdPageContentStream.close();//关闭流
                    document.save(triageSlipParamDto.getResponse().getOutputStream());//将文档保存到输出流中
                    document.close();//关闭文档
                    new File(filePath).delete();//最后删除条形码图片
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 个人登记 - 腕带_列表or绑定or解除
     */
    @Override
    public Object spireLamella(final SpireLamellaParamDto spireLamellaParamDto) {

        final Map<String, Object> result = Maps.newHashMap();
        //处理分页
        spireLamellaParamDto.setCurrent(spireLamellaParamDto.getCurrent() * spireLamellaParamDto.getPageSize() - spireLamellaParamDto.getPageSize());
        //查询腕带信息
        final List<GetSpireLamellaResultDto> allSpireLamella = this.medClinicCardInfoMapper.getTagList();
        //查询分诊主表数据量
        final Long aLong = this.medClinicCheckMasterMapper.selectCount(Wrappers.<MedClinicCheckMasterEntity>lambdaQuery());
        //查询人员分诊信息
        final RegListsParamDto regListsParamDto = new RegListsParamDto();
        regListsParamDto.setPageSize(aLong);
        final Map<String, Object> map = this.regList(regListsParamDto);
        if (map.size() > CommonConstants.ONE) {
            final IPage<RegListsResultDto> iPage = (IPage<RegListsResultDto>) map.get("iPage");
            final List<RegListsResultDto> records = iPage.getRecords();
            //处理数据映射
            Optional.ofNullable(records).filter(rList -> rList.size() > CommonConstants.ZERO).ifPresent(rList -> {
                Optional.ofNullable(allSpireLamella).filter(aslList -> aslList.size() > CommonConstants.ZERO).ifPresent(aslList -> {
                    aslList.forEach(aslDto -> {
                        try {
                            final RegListsResultDto regListsResultDto =
                                    rList.stream().filter(rDto -> null != rDto.getTagNo() && aslDto.getValue().equals(rDto.getTagNo())).findFirst().get();
                            if (!ObjectUtils.isEmpty(regListsResultDto)) {
                                aslDto.setName(regListsResultDto.getName());
                                aslDto.setRegId(regListsResultDto.getRegId());
                            }
                        } catch (final NoSuchElementException e) {
                            return;
                        }
                    });
                });
            });
        }
        //封装结果集
        if (!CollectionUtils.isEmpty(allSpireLamella)) {
            List<GetSpireLamellaResultDto> list = Lists.newArrayList();
            if (StringUtils.isNotBlank(spireLamellaParamDto.getSearchValue())) {
                //处理查询-腕带名称
                list.addAll(allSpireLamella.stream().filter(aslDto -> null != aslDto.getLabel() && aslDto.getLabel().contains(spireLamellaParamDto.getSearchValue())).collect(Collectors.toList()));
                //处理查询-腕带编码
                list.addAll(allSpireLamella.stream().filter(aslDto -> null != aslDto.getValue() && aslDto.getValue().contains(spireLamellaParamDto.getSearchValue())).collect(Collectors.toList()));
                //处理查询-患者姓名
                list.addAll(allSpireLamella.stream().filter(aslDto -> null != aslDto.getName() && aslDto.getName().contains(spireLamellaParamDto.getSearchValue())).collect(Collectors.toList()));
                //处理查询-急诊编号
                list.addAll(allSpireLamella.stream().filter(aslDto -> null != aslDto.getRegId() && aslDto.getRegId().contains(spireLamellaParamDto.getSearchValue())).collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(list)) {
                    //过滤重复的数据
                    final Set<GetSpireLamellaResultDto> set =
                            new TreeSet<GetSpireLamellaResultDto>((aslSet1, aslSet2) -> aslSet1.getValue().compareTo(aslSet2.getValue()));
                    set.addAll(list);
                    list = new ArrayList<>(set);
                }
            }
            //处理分页
            if (!CollectionUtils.isEmpty(list)) {
                list = list.stream().sorted(Comparator.comparing(GetSpireLamellaResultDto::getValue)).collect(Collectors.toList());
            } else {
                if (StringUtils.isNotBlank(spireLamellaParamDto.getSearchValue())) {
                    return list;
                } else {
                    list = allSpireLamella;
                }
            }
            switch (spireLamellaParamDto.getAnchorPoint()) {
                case CommonConstants.ZERO:
                    //未绑定(不分页)
                    return list.stream().filter(aslDto -> aslDto.getRegId() == null).collect(Collectors.toList());
                case CommonConstants.ONE:
                    //已绑定(分页)
                    final List<GetSpireLamellaResultDto> collect = list.stream().filter(aslDto -> aslDto.getRegId() != null).skip(spireLamellaParamDto.getCurrent()).limit(spireLamellaParamDto.getPageSize()).collect(Collectors.toList());
                    result.put("current", spireLamellaParamDto.getCurrent() + 1);
                    result.put("pages", list.size() / spireLamellaParamDto.getPageSize() + (list.size() % spireLamellaParamDto.getPageSize() != 0 ? 1 : 0));
                    result.put("records", collect);
                    result.put("size", spireLamellaParamDto.getPageSize());
                    result.put("total", list.size());
                    return result;
                case CommonConstants.TWO:
                    //解除绑定
                    if (!CollectionUtils.isEmpty(spireLamellaParamDto.getRegList())) {
                        return this.medClinicCheckMasterMapper.update(null,
                                Wrappers.<MedClinicCheckMasterEntity>lambdaUpdate().set(MedClinicCheckMasterEntity::getTagNo, "").in(MedClinicCheckMasterEntity::getRegId, spireLamellaParamDto.getRegList()));
                    }
            }
        }
        return null;
    }

    /**
     * 个人登记 - 腕带_PDF
     */
    @Override
    public void printWristbands(final PrintWristbandsParamDto printWristbandsParamDto) {

        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0014.getValue());
        //查询患者分诊信息
        final MedClinicCheckMasterEntity medClinicCheckMasterEntity = this.medClinicCheckMasterMapper.selectById(printWristbandsParamDto.getRegId());
        if (!ObjectUtils.isEmpty(medClinicCheckMasterEntity)) {
            //分诊级别
            List<MedDictionaryEntity> drDtoList = dictByCode.get(DictionaryEnum.PIAT_0009.getValue());
            if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(medClinicCheckMasterEntity.getResetLevel())) {
                for (final MedDictionaryEntity drDto : drDtoList) {
                    if (medClinicCheckMasterEntity.getResetLevel().equals(drDto.getItemcode())) {
                        medClinicCheckMasterEntity.setResetLevel(drDto.getItemname());
                    }
                }
            }
            //就诊科室
            drDtoList = dictByCode.get(DictionaryEnum.PIAT_0014.getValue());
            if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(medClinicCheckMasterEntity.getDepartmentId())) {
                for (final MedDictionaryEntity drDto : drDtoList) {
                    if (medClinicCheckMasterEntity.getDepartmentId().equals(drDto.getItemcode())) {
                        medClinicCheckMasterEntity.setDepartmentId(drDto.getItemname());
                    }
                }
            }
            //查询患者生命体征
            MedClinicVitalsignsEntity medClinicVitalsignsEntity = null;
            final List<MedClinicVitalsignsEntity> list = this.medClinicVitalsignsFunction.list(Wrappers.<MedClinicVitalsignsEntity>lambdaQuery().eq(MedClinicVitalsignsEntity::getRegId, medClinicCheckMasterEntity.getRegId()).orderByDesc(MedClinicVitalsignsEntity::getAcqTime));
            if (!CollectionUtils.isEmpty(list)) {
                medClinicVitalsignsEntity = list.get(CommonConstants.ZERO);
            }
            //查询患者个人信息
            final MedClinicCardInfoEntity entity = this.medClinicCardInfoMapper.selectById(medClinicCheckMasterEntity.getInfoId());
            if (!ObjectUtils.isEmpty(entity)) {
                final HttpServletResponse response = printWristbandsParamDto.getResponse();
                try {
                    // 设置响应头，控制浏览器下载该文件
                    printWristbandsParamDto.getResponse().setHeader("content-disposition", "attachment;filename="
                            + URLEncoder.encode("腕带.pdf", "UTF-8"));
                    final PDDocument document = new PDDocument();//创建文档
                    final PDPage pdPage = new PDPage(new PDRectangle(354.0F, 177.0F));//创建一个空白页,并设置纸张大小
                    document.addPage(pdPage);//将空白页添加到文档中
                    final PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
                    final PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流

                    //生成二维码
                    final QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bitMatrix = null;
                    final String contents = StringUtils.isNotBlank(entity.getPatientId()) ? entity.getPatientId() : medClinicCheckMasterEntity.getRegId();
                    bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 100, 100);
                    final String filePath = String.format("\\%s.png", contents);
                    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
                    final PDImageXObject image = PDImageXObject.createFromFile(filePath, document);
                    pdPageContentStream.drawImage(image, 0F, 40F);
                    pdPageContentStream.drawImage(image, 255, 40);

                    this.ContentStream(pdPageContentStream, font, 10F, RenderingMode.FILL, null, 150F, 134F, "xx人民医院");
                    this.ContentStream(pdPageContentStream, null, null, null, null, 90F, 114F, String.format("姓名:%s", entity.getName()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 170F, 114F, String.format("性别:%s", CommonConstants.STR_ZERO_ONE.equals(entity.getGender()) ? "男" : CommonConstants.STR_ZERO_TWO.equals(entity.getGender()) ? "女" : "未知"));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 224F, 114F, String.format("年龄:%s", String.valueOf(medClinicCheckMasterEntity.getRegAge())));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 90F, 87F, String.format("病历号:%s", medClinicCheckMasterEntity.getRegId()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 200F, 87F, String.format("级别:%s", medClinicCheckMasterEntity.getResetLevel()));
                    this.ContentStream(pdPageContentStream, null, null, null, null, 90F, 60F, String.format("就诊科室:%s", medClinicCheckMasterEntity.getDepartmentId()));
                    this.ContentStream(pdPageContentStream, font, 13F, RenderingMode.FILL_CLIP, null, 216F, 60F, "抢13");

                    pdPageContentStream.close();//关闭流
                    document.save(printWristbandsParamDto.getResponse().getOutputStream());//将文档保存到输出流中
                    document.close();//关闭文档
                    new File(filePath).delete();//最后删除条形码图片
                } catch (final IOException | WriterException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param pdPageContentStream 内容流
     * @param font                字体
     * @param fontSize            字体大小
     * @param renderingMode       字体粗细
     * @param leading             行间距
     * @param x                   输出内容x轴
     * @param y                   输出内容y轴
     * @param str                 输出内容
     * @throws IOException
     */
    void ContentStream(final PDPageContentStream pdPageContentStream, final PDType0Font font, final Float fontSize, final RenderingMode renderingMode, final Float leading, final Float x, final Float y, @org.springframework.lang.NonNull final String str) throws IOException {

        pdPageContentStream.beginText();//开始文本
        if (Objects.nonNull(fontSize)) {
            pdPageContentStream.setFont(font, fontSize);//设置文本字体
        }
        if (Objects.nonNull(leading)) {
            pdPageContentStream.setLeading(leading);//行间距
        }
        if (Objects.nonNull(renderingMode)) {
            pdPageContentStream.setRenderingMode(renderingMode);//可设置字体粗细
        }
        pdPageContentStream.newLineAtOffset(x, y);//设置文本的位置
        pdPageContentStream.showText(StringUtils.isNotBlank(str) ? str : "");//内容
        pdPageContentStream.endText();//结束文本
    }

    /**
     * @param pdPage    页面
     * @param font      字体
     * @param fontSize  字体大小
     * @param marginTop 顶部边距
     * @param str       内容
     * @return Map<String, Float> ——> map.get("x");map.get("y")
     * @throws IOException
     */
    Map<String, Float> centerCoordinate(final PDPage pdPage, final PDType0Font font, final Float fontSize, final Float marginTop, final String str) throws IOException {

        final Map<String, Float> result = new HashMap();
        if (StringUtils.isNotBlank(str)) {
            final float titleWidth = font.getStringWidth(str) / 1000 * fontSize;
            final float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
            result.put("x", (pdPage.getMediaBox().getWidth() - titleWidth) / 2);
            result.put("y", pdPage.getMediaBox().getHeight() - marginTop - titleHeight);
        }
        return result;
    }

    /**
     * @param pdPageContentStream 内容输出流
     * @param font                字体
     * @param height              高
     * @param list                输出内容列表
     * @param sign                标记(0-表头;1-四列内容;2-三列内容)
     * @throws IOException
     */
    void frameLine(final PDPageContentStream pdPageContentStream, final PDType0Font font, final Float height, final List<String> list, final Integer sign) throws IOException {

        //上横线
        pdPageContentStream.moveTo(30F, height);
        pdPageContentStream.lineTo(565F, height);
        pdPageContentStream.stroke();
        //第一竖线
        final Float verticalLineOne = 30F;
        pdPageContentStream.moveTo(verticalLineOne, height);
        pdPageContentStream.lineTo(verticalLineOne, height - 25);
        pdPageContentStream.stroke();
        //第二竖线
        final Float verticalLineTwo = 130F;
        pdPageContentStream.moveTo(verticalLineTwo, height);
        pdPageContentStream.lineTo(verticalLineTwo, height - 25);
        pdPageContentStream.stroke();
        //第三竖线
        final Float verticalLineThree = 235F;
        pdPageContentStream.moveTo(verticalLineThree, height);
        pdPageContentStream.lineTo(verticalLineThree, height - 25);
        pdPageContentStream.stroke();
        //第四竖线
        final Float verticalLineFour = 490F;
        pdPageContentStream.moveTo(verticalLineFour, height);
        pdPageContentStream.lineTo(verticalLineFour, height - 25);
        pdPageContentStream.stroke();
        //第五竖线
        final Float verticalLineFive = 565F;
        pdPageContentStream.moveTo(verticalLineFive, height);
        pdPageContentStream.lineTo(verticalLineFive, height - 25);
        pdPageContentStream.stroke();
        //下横线
        pdPageContentStream.moveTo(30F, height - 25);
        pdPageContentStream.lineTo(565F, height - 25);
        pdPageContentStream.stroke();

        final Float heightTxt = height - 20;

        if (!CollectionUtils.isEmpty(list)) {
            switch (sign) {
                case 0:
                    this.ContentStream(pdPageContentStream, font, 12F, null, null, (verticalLineThree - verticalLineTwo) / 2 + verticalLineTwo - font.getStringWidth(list.get(0)) / 1000 * 12 / 2, heightTxt, list.get(0));
                    this.ContentStream(pdPageContentStream, null, null, null, null, (verticalLineFour - verticalLineThree) / 2 + verticalLineThree - font.getStringWidth(list.get(1)) / 1000 * 12 / 2, heightTxt, list.get(1));
                    this.ContentStream(pdPageContentStream, null, null, null, null, (verticalLineFive - verticalLineFour) / 2 + verticalLineFour - font.getStringWidth(list.get(2)) / 1000 * 12 / 2, heightTxt, list.get(2));
                    break;
                case 1:
                    this.ContentStream(pdPageContentStream, font, 10F, null, null, (verticalLineTwo - verticalLineOne) / 2 + verticalLineOne - font.getStringWidth(list.get(0)) / 1000 * 10 / 2, heightTxt, list.get(0));
                    this.ContentStream(pdPageContentStream, null, null, null, null, (verticalLineThree - verticalLineTwo) / 2 + verticalLineTwo - font.getStringWidth(list.get(1)) / 1000 * 10 / 2, heightTxt, list.get(1));
                    this.ContentStream(pdPageContentStream, null, null, null, null, verticalLineThree + 2, heightTxt, list.get(2));
                    this.ContentStream(pdPageContentStream, null, null, null, null, (verticalLineFive - verticalLineFour) / 2 + verticalLineFour - font.getStringWidth(list.get(3)) / 1000 * 10 / 2, heightTxt, list.get(3));
                    break;
                case 2:
                    break;
            }
        }
    }
}
