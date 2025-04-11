package com.mdsd.cloud.controller.diagnosis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.diagnosis.enums.PrintDeliveryRecordEnum;
import com.mdsd.cloud.controller.diagnosis.function.MedPhepShiftRecordFunction;
import com.mdsd.cloud.controller.diagnosis.service.DictManager;
import com.mdsd.cloud.controller.diagnosis.service.MedPhepShiftRecordService;
import com.mdsd.cloud.controller.diagnosis.vo.PrintDeliveryRecordRo;
import com.mdsd.cloud.controller.diagnosis.vo.PrintShiftRecordDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ShiftRecordDetailVo;
import com.mdsd.cloud.controller.diagnosis.vo.ShiftSaveOrUpdateVo;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.entity.*;
import com.mdsd.cloud.enumeration.ValidEnum;
import com.mdsd.cloud.function.MedPhepGeneralInspectionsFunction;
import com.mdsd.cloud.mapper.*;
import com.mdsd.cloud.response.exception.BusinessException;
import com.mdsd.cloud.utils.FileUtil;
import com.mdsd.cloud.utils.PngResponseUtil;
import com.mdsd.cloud.utils.TemplateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huxiaoneng
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MedPhepShiftRecordServiceImpl implements MedPhepShiftRecordService {

    private final MedPhepShiftRecordMapper medPhepShiftRecordMapper;

    private final MedPhepGeneralInspectionsMapper medPhepGeneralInspectionsMapper;

    private final MedPhepEstimateMapper medPhepEstimateMapper;//评分主表

    private final MedPhepMeasuresMapper medPhepMeasuresMapper;//急救措施

    private final MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;

    private final MedPhepEmergencyOrdersMapper medPhepEmergencyOrdersMapper;

    private final MedPhepShiftRecordFunction medPhepShiftRecordFunction;

    private final MedPhepGeneralInspectionsFunction medPhepGeneralInspectionsFunction;

    private final DictManager dictManager;

    private final DataDictionaryService dataDictionaryService;//数据字典

    /**
     * 新增or修改
     *
     * @param shiftSaveOrUpdateVo 入参
     */
    @Override
    public void saveOrUpdateShift(final ShiftSaveOrUpdateVo shiftSaveOrUpdateVo) {
        //查询交接单
        final MedPhepShiftRecordEntity medPhepShiftRecordEntity = shiftSaveOrUpdateVo.getMedPhepShiftRecordEntity();
        final LambdaQueryWrapper<MedPhepShiftRecordEntity> shiftQueryWrapper = new LambdaQueryWrapper<>();
        shiftQueryWrapper.eq(MedPhepShiftRecordEntity::getPatientId, medPhepShiftRecordEntity.getPatientId())
                .eq(MedPhepShiftRecordEntity::getSubmitStatus, ValidEnum.VALID.getValue());
        //查询体格表信息
        if (Objects.nonNull(shiftSaveOrUpdateVo.getMedPhepGeneralInspectionsEntity())) {
            final MedPhepGeneralInspectionsEntity medPhepGeneralInspectionsEntity = shiftSaveOrUpdateVo.getMedPhepGeneralInspectionsEntity();
            final LambdaQueryWrapper<MedPhepGeneralInspectionsEntity> genQueryWrapper = new LambdaQueryWrapper<>();
            genQueryWrapper.eq(MedPhepGeneralInspectionsEntity::getPatientId, medPhepGeneralInspectionsEntity.getPatientId())
                    .eq(MedPhepGeneralInspectionsEntity::getIsHand, ValidEnum.VALID.getValue())
                    .eq(MedPhepGeneralInspectionsEntity::getStatus, ValidEnum.VALID.getValue());
            medPhepShiftRecordEntity.setSubmitStatus(ValidEnum.VALID.getValue());
            medPhepGeneralInspectionsEntity.setIsHand(ValidEnum.VALID.getValue());
            medPhepGeneralInspectionsEntity.setStatus(ValidEnum.VALID.getValue());
            this.medPhepGeneralInspectionsFunction.saveOrUpdate(medPhepGeneralInspectionsEntity, genQueryWrapper);
        }
        this.medPhepShiftRecordFunction.saveOrUpdate(medPhepShiftRecordEntity, shiftQueryWrapper);

    }

    /**
     * 根据ID查询交接单
     *
     * @param patientId 患者ID
     * @return message
     */
    @Override
    public MedPhepShiftRecordEntity queryById(final String patientId) {

        final LambdaQueryWrapper<MedPhepShiftRecordEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedPhepShiftRecordEntity::getPatientId, patientId)
                .eq(MedPhepShiftRecordEntity::getSubmitStatus, ValidEnum.VALID.getValue());

        return this.medPhepShiftRecordMapper.selectOne(queryWrapper);
    }

    @Override
    public ShiftRecordDetailVo queryDetail(final String patientId) {
        //获取交接信息
        final MedPhepShiftRecordEntity medPhepShiftRecordEntity = this.medPhepShiftRecordMapper.selectOne(Wrappers.<MedPhepShiftRecordEntity>lambdaQuery()
                .eq(MedPhepShiftRecordEntity::getPatientId, patientId)
                .eq(MedPhepShiftRecordEntity::getSubmitStatus, ValidEnum.VALID.getValue()));
        //获取评分信息
        final List<MedPhepEstimateEntity> medPhepEstimateEntities = this.medPhepEstimateMapper.selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery()
                .eq(MedPhepEstimateEntity::getPatientId, patientId));
        //获取心电图数量
        final Long ecgCount = new MedPhepAttachmentEntity().selectCount(Wrappers.<MedPhepAttachmentEntity>lambdaQuery().eq(MedPhepAttachmentEntity::getDeleteFlag, 0).eq(MedPhepAttachmentEntity::getBusinessId, patientId).eq(MedPhepAttachmentEntity::getFileType, 0));
        //获取急救措施
        final List<MedPhepMeasuresEntity> medPhepMeasuresEntities = this.medPhepMeasuresMapper.selectList(Wrappers.<MedPhepMeasuresEntity>lambdaQuery().eq(MedPhepMeasuresEntity::getPatientId, patientId));
        //获取病历信息
        final MedPhepMedicalRecordsEntity medicalRecordsEntity = this.medPhepMedicalRecordsMapper.selectOne(Wrappers.<MedPhepMedicalRecordsEntity>lambdaQuery().eq(MedPhepMedicalRecordsEntity::getPatientId, patientId));
        //体格检查
        final List<MedPhepGeneralInspectionsEntity> medPhepGeneralInspectionsEntities = this.medPhepGeneralInspectionsMapper.selectList(Wrappers.<MedPhepGeneralInspectionsEntity>lambdaQuery().eq(MedPhepGeneralInspectionsEntity::getPatientId, patientId).orderByDesc(MedPhepGeneralInspectionsEntity::getCreateTime));

        final ShiftRecordDetailVo shiftRecordDetailVo = new ShiftRecordDetailVo();
        shiftRecordDetailVo.setEstimateEntityList(medPhepEstimateEntities);
        shiftRecordDetailVo.setEcgCount(Long.bitCount(ecgCount));
        shiftRecordDetailVo.setMeasuresEntities(medPhepMeasuresEntities);
        shiftRecordDetailVo.setMedPhepMedicalRecordsEntity(medicalRecordsEntity);
        shiftRecordDetailVo.setMedPhepGeneralInspectionsEntity(medPhepGeneralInspectionsEntities);
        shiftRecordDetailVo.setMedPhepShiftRecordEntity(medPhepShiftRecordEntity);

        return shiftRecordDetailVo;
    }

    @Override
    public void printShiftRecord(final String templateId, final String patientId, final HttpServletResponse response) {

        //查询数据模板
        final List<MedTemplateEntity> templateEntities = new MedTemplateEntity().selectList(Wrappers.<MedTemplateEntity>lambdaQuery().eq(MedTemplateEntity::getTemplateId, templateId));
        final MedTemplateEntity medTemplateEntity = templateEntities.stream().filter(t -> "graphics2D".equals(t.getContent())).findAny().orElse(null);

        //获取病历信息
        final PrintShiftRecordDetailVo shiftRecordDetailVo = this.medPhepShiftRecordMapper.queryShiftRecord(patientId);

        final String[] accessoryExamSplits = shiftRecordDetailVo.getAccessoryExam().split("、");
        final StringBuilder accessoryBuilder = new StringBuilder();
        for (int i = 0; i < accessoryExamSplits.length; i++) {
            accessoryBuilder.append(accessoryExamSplits[i]);
            if (i != 2) {
                accessoryBuilder.append("、");
            } else {
                accessoryBuilder.append("|");
            }
        }
        //辅助检查
        shiftRecordDetailVo.setAccessoryExam(accessoryBuilder.substring(0, accessoryBuilder.length() - 1));
        //获取急救措施
        final List<MedPhepMeasuresEntity> medPhepMeasuresEntities = this.medPhepMeasuresMapper.selectList(Wrappers.<MedPhepMeasuresEntity>lambdaQuery()
                .eq(MedPhepMeasuresEntity::getPatientId, patientId));
        final StringBuilder sbu = new StringBuilder();
        for (final MedPhepMeasuresEntity measuresEntity :
                medPhepMeasuresEntities) {
            if (!sbu.toString().equals("")) {
                sbu.append("、");
            }
            sbu.append(measuresEntity.getMeasureName());
        }
        shiftRecordDetailVo.setTreatment(sbu.toString());
        //获取用药信息
        final List<MedPhepEmergencyOrdersEntity> medPhepEmergencyOrdersEntities = this.medPhepEmergencyOrdersMapper.selectList(Wrappers.<MedPhepEmergencyOrdersEntity>lambdaQuery()
                .eq(MedPhepEmergencyOrdersEntity::getPatientId, patientId)
                .eq(MedPhepEmergencyOrdersEntity::getStatus, ValidEnum.VALID.getValue()));
        final List<MedPhepDrugEntity> medPhepDrugsEntities = new MedPhepDrugEntity().selectAll();
        if (!CollectionUtils.isEmpty(medPhepEmergencyOrdersEntities) && !CollectionUtils.isEmpty(medPhepDrugsEntities)) {
            //查询数据字典(PHFA_0056: 药品用法)
            final List<MedDictionaryEntity> phfa_0056 = new MedDictionaryEntity().selectList(Wrappers.<MedDictionaryEntity>lambdaQuery().eq(MedDictionaryEntity::getCode, "PHFA_0056"));
            final StringBuilder drugSb = new StringBuilder();
            for (final MedPhepEmergencyOrdersEntity entity :
                    medPhepEmergencyOrdersEntities) {
                for (final MedPhepDrugEntity drugsEntity : medPhepDrugsEntities) {
                    if (entity.getDrugId().equals(drugsEntity.getId())) {
                        drugSb.append(entity.getCreateTime())
                                .append(drugsEntity.getName())
                                .append(phfa_0056.stream().filter(ent -> entity.getUsageId().equals(ent.getItemcode())).findFirst().get().getItemname())
                                .append(entity.getDose());
                    }
                }
                drugSb.append("|");
            }
            drugSb.append(shiftRecordDetailVo.getOtherDruge());
            shiftRecordDetailVo.setDruge(drugSb.toString());
        }
        final MedPhepGeneralInspectionsEntity generalInspectionsEntity = this.medPhepGeneralInspectionsMapper.selectOne(Wrappers.<MedPhepGeneralInspectionsEntity>lambdaQuery()
                .eq(MedPhepGeneralInspectionsEntity::getPatientId, patientId)
                .eq(MedPhepGeneralInspectionsEntity::getIsHand, ValidEnum.VALID.getValue())
                .eq(MedPhepGeneralInspectionsEntity::getStatus, ValidEnum.VALID.getValue()));
        final String path = Objects.requireNonNull(this.getClass().getResourceAsStream("/")).toString();//系统路径
        final String pngPathFile = path + "shiftRecord" + shiftRecordDetailVo.getRecordId() + ".png";
        try {
            if (generalInspectionsEntity == null) {
                throw new RuntimeException("数据有问题,确认是否有提交患者交接单");
            }
            final Field[] shiftRecordFields = shiftRecordDetailVo.getClass().getDeclaredFields();
            final Field[] generalInspectionsFields = generalInspectionsEntity.getClass().getDeclaredFields();
            for (final MedTemplateEntity entity : templateEntities) {
                StringBuilder sb = new StringBuilder();
                if (entity.getContent() == null) {
                    sb.append("");
                } else {
                    sb.append(entity.getContent());
                }
                //根据模板匹配实际字段信息
                if (entity.getContentValue() != null) {
                    sb = this.getValue(sb, shiftRecordFields, entity, shiftRecordDetailVo);
                    sb = this.getValue(sb, generalInspectionsFields, entity, generalInspectionsEntity);
                }
                entity.setContent(sb.toString());
            }
            TemplateUtil.imageBuilder(shiftRecordDetailVo.getRecordId(), pngPathFile, templateEntities, medTemplateEntity);
            PngResponseUtil.buildPngDocument(pngPathFile, response);
            FileUtil.deleteFile(pngPathFile);
            return;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("图片生成失败,请确认数据是否正常录入");
    }

    /**
     * 急救交接单 - 打印交接记录
     */
    @Override
    public PrintDeliveryRecordRo printDeliveryRecord(final String patientId) {

        Assert.notNull(patientId, "患者ID为: NULL");
        final PrintDeliveryRecordRo result = new PrintDeliveryRecordRo();
        //查询需打印数据
        final Map<String, Object> printDeliveryRecordMap = this.medPhepShiftRecordMapper.printDeliveryRecord(patientId);
        if (!CollectionUtils.isEmpty(printDeliveryRecordMap)) {
            //查询数据字典获取枚举值
            final GetDictByCodesPao getDictByCodesPao = new GetDictByCodesPao();
            getDictByCodesPao.setCode(new ArrayList<String>() {{
                this.add("PHFA_0001");//PHFA_0001:性别
                this.add("PHFA_0002");//PHFA_0002:年龄类别
                this.add("PHFA_0007");//PHFA_0007:出车分站
                this.add("PHFA_0016");//PHFA_0016:意识
                this.add("PHFA_0017");//PHFA_0017:皮肤
                this.add("PHFA_0018");//PHFA_0018:瞳孔
                this.add("PHFA_0019");//PHFA_0019:对光反射
                this.add("PHFA_0057");//PHFA_0057:初步诊断
            }});
            final Map<String, List<MedDictionaryEntity>> dictByCodeMap = this.dataDictionaryService.getDictByCode(getDictByCodesPao);
            //处理原始数据
            this.handleRawData(printDeliveryRecordMap, "021", dictByCodeMap, "PHFA_0001");//性别
            this.handleRawData(printDeliveryRecordMap, "023", dictByCodeMap, "PHFA_0002");//年龄类别
            this.handleRawData(printDeliveryRecordMap, "230", dictByCodeMap, "PHFA_0007");//出车分站
            this.handleRawData(printDeliveryRecordMap, "150", dictByCodeMap, "PHFA_0016");//意识
            this.handleRawData(printDeliveryRecordMap, "160", dictByCodeMap, "PHFA_0017");//皮肤
            this.handleRawData(printDeliveryRecordMap, "130", dictByCodeMap, "PHFA_0018");//瞳孔
            this.handleRawData(printDeliveryRecordMap, "140", dictByCodeMap, "PHFA_0019");//对光反射
            this.handleRawData(printDeliveryRecordMap, "040", dictByCodeMap, "PHFA_0057");//初步诊断
            //查询急救措施
            final List<MedPhepMeasuresEntity> mpmList = this.medPhepMeasuresMapper.selectList(Wrappers.<MedPhepMeasuresEntity>lambdaQuery().select(MedPhepMeasuresEntity::getMeasureName).eq(MedPhepMeasuresEntity::getPatientId, printDeliveryRecordMap.get("PATIENT_ID")));
            //查询已用药物

            //辅助检查
            final List<MedPhepEstimateEntity> mpeList = this.medPhepEstimateMapper.selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().select(MedPhepEstimateEntity::getName, MedPhepEstimateEntity::getDescription).eq(MedPhepEstimateEntity::getPatientId, printDeliveryRecordMap.get("PATIENT_ID")));
            List<String> collect = null;
            if (CollectionUtils.isNotEmpty(mpeList)) {
                collect = mpeList.stream().map(MedPhepEstimateEntity::getName).collect(Collectors.toList());
            }
            //拼接打印数据
            final List<Map<String, Object>> mapList = Lists.newArrayList();
            for (final PrintDeliveryRecordEnum enu : PrintDeliveryRecordEnum.values()) {
                final Map<String, Object> map = Maps.newHashMap();
                map.put("type", enu.getType());
                switch (PrintDeliveryRecordEnum.getEnumByKey(enu.getKey())) {
                    case PDR_000://标题
                        map.put("doubleSize", true);
                        map.put("value", enu.getValue());
                        break;
                    case PDR_010://编号
                        map.put("value", String.format(enu.getValue(), ""));
                        break;
                    case PDR_020://姓名、性别、年龄
                        map.put("value", String.format(enu.getValue(), Objects.nonNull(printDeliveryRecordMap.get("020")) ? printDeliveryRecordMap.get("020") : "", Objects.nonNull(printDeliveryRecordMap.get("021")) ? printDeliveryRecordMap.get("021") : "", null != printDeliveryRecordMap.get("022") ? printDeliveryRecordMap.get("022") : "", Objects.nonNull(printDeliveryRecordMap.get("023")) ? printDeliveryRecordMap.get("023") : ""));
                        break;
                    case PDR_070://急救措施
                        map.put("value", String.format(enu.getValue(), CollectionUtils.isNotEmpty(mpmList) ? mpmList.stream().map(MedPhepMeasuresEntity::getMeasureName).collect(Collectors.joining("/")) : ""));
                        break;
                    case PDR_100://体温、脉搏、呼吸
                        map.put("value", String.format(enu.getValue(), Objects.nonNull(printDeliveryRecordMap.get("100")) ? printDeliveryRecordMap.get("100") + "℃" : "未检查", Objects.nonNull(printDeliveryRecordMap.get("101")) ? printDeliveryRecordMap.get("101") + "次/分" : "", null != printDeliveryRecordMap.get("102") ? printDeliveryRecordMap.get("102") + "次/分" : ""));
                        break;
                    case PDR_110://收缩压、舒张压、血氧
                        map.put("value", String.format(enu.getValue(), Objects.nonNull(printDeliveryRecordMap.get("110")) ? printDeliveryRecordMap.get("110") : "", Objects.nonNull(printDeliveryRecordMap.get("111")) ? printDeliveryRecordMap.get("111") : "", null != printDeliveryRecordMap.get("112") ? printDeliveryRecordMap.get("112") + "%" : ""));
                        break;
                    case PDR_130://瞳孔左、瞳孔右
                        map.put("value", String.format(enu.getValue(), Objects.nonNull(printDeliveryRecordMap.get("130")) ? printDeliveryRecordMap.get("130") : "", Objects.nonNull(printDeliveryRecordMap.get("130")) ? printDeliveryRecordMap.get("130") : ""));
                        break;
                    case PDR_140://对光反射左、对光反射右
                        map.put("value", String.format(enu.getValue(), Objects.nonNull(printDeliveryRecordMap.get("140")) ? printDeliveryRecordMap.get("140") : "", Objects.nonNull(printDeliveryRecordMap.get("140")) ? printDeliveryRecordMap.get("140") : ""));
                        break;
                    case PDR_171://TI评估、GCS评估
                        if (CollectionUtils.isNotEmpty(mpeList)) {
                            map.put("value", String.format(enu.getValue(), collect.contains("ti") ? mpeList.stream().filter(ent -> ent.getName().equals("ti")).findFirst().get().getDescription() : "", collect.contains("gcs") ? mpeList.stream().filter(ent -> ent.getName().equals("gcs")).findFirst().get().getDescription() : ""));
                        }
                        break;
                    case PDR_172://胸痛评估、Killip评估
                        if (CollectionUtils.isNotEmpty(mpeList)) {
                            map.put("value", String.format(enu.getValue(), collect.contains("chestPain") ? mpeList.stream().filter(ent -> ent.getName().equals("chestPain")).findFirst().get().getDescription() : "", collect.contains("killip") ? mpeList.stream().filter(ent -> ent.getName().equals("killip")).findFirst().get().getDescription() : ""));
                        }
                        break;
                    case PDR_173://FAST评估
                        if (CollectionUtils.isNotEmpty(mpeList)) {
                            map.put("value", String.format(enu.getValue(), collect.contains("fast") ? mpeList.stream().filter(ent -> ent.getName().equals("fast")).findFirst().get().getDescription() : ""));
                        }
                        break;
                    case PDR_220://生成二维码
                        try {
                            final QRCodeWriter qrCodeWriter = new QRCodeWriter();
                            final BitMatrix bitMatrix = qrCodeWriter.encode(printDeliveryRecordMap.get("MISSION_ID").toString(), BarcodeFormat.QR_CODE, 300, 300);
                            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
                            map.put("value", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
                        } catch (final WriterException e) {
                            throw new BusinessException("生成二维码异常:", e.getMessage());
                        } catch (final IOException e) {
                            throw new BusinessException("生成二维码异常:", e.getMessage());
                        }
                        break;
                    default:
                        map.put("value", String.format(enu.getValue(), null != printDeliveryRecordMap.get(enu.getKey()) ? printDeliveryRecordMap.get(enu.getKey()).toString() : ""));
                        break;
                }
                mapList.add(map);
            }
            result.setItems(mapList);
        }
        return result;
    }

    /**
     * 打印交接记录 - 处理原始数据
     *
     * @param dictCode 字典code
     * @param dataKey  原始数据key
     */
    void handleRawData(final Map<String, Object> printDeliveryRecordMap, final String dataKey, final Map<String, List<MedDictionaryEntity>> dictByCodeMap, final String dictCode) {

        printDeliveryRecordMap.put(dataKey, null != printDeliveryRecordMap.get(dataKey) ? dictByCodeMap.get(dictCode).stream().filter(ent -> printDeliveryRecordMap.get(dataKey).equals(ent.getItemcode())).findFirst().get().getItemname() : null);
    }

    /**
     * 重复使用方法，匹配字段信息
     *
     * @param sb     stringBuilder
     * @param fields class
     * @param entity 模板对象
     * @param tClass 实体类对象
     * @param <T>    泛型
     * @return 返回值StringBuilder
     */
    public <T> StringBuilder getValue(final StringBuilder sb, final Field[] fields, final MedTemplateEntity entity, final T tClass) {

        String value;
        try {
            for (final Field field : fields) {
                if (entity.getContentValue().equals(field.getName())) {
                    field.setAccessible(true);
                    System.out.println(field.get(tClass));
                    if (field.get(tClass) != null) {
                        value = String.valueOf(field.get(tClass));
                        value = this.dictManager.matchingValue(entity.getContentValue(), value);
                        sb.append(value);
                    }

                }
            }
            return sb;
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb;
    }

}
