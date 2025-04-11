package com.mdsd.cloud.controller.groupevents.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.groupevents.dto.*;
import com.mdsd.cloud.controller.groupevents.entity.MedClinicGroupEventsEntity;
import com.mdsd.cloud.controller.groupevents.entity.TemplateEntity;
import com.mdsd.cloud.controller.groupevents.function.MedClinicCardInfoFunction;
import com.mdsd.cloud.controller.groupevents.function.MedClinicCheckMasterFunction;
import com.mdsd.cloud.controller.groupevents.mapper.MedClinicGroupEventsMapper;
import com.mdsd.cloud.controller.groupevents.service.GroupEventsService;
import com.mdsd.cloud.controller.knowledgebase.entity.PatientSayEntity;
import com.mdsd.cloud.controller.knowledgebase.mapper.PatientSayMapper;
import com.mdsd.cloud.controller.registration.entity.MedClinicCardInfoEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicCheckMasterEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicVitalsignsEntity;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCardInfoMapper;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCheckMasterMapper;
import com.mdsd.cloud.controller.registration.mapper.MedClinicVitalsignsMapper;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import com.mdsd.cloud.utils.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hxn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupEventsServiceImpl extends ServiceImpl<MedClinicGroupEventsMapper, MedClinicGroupEventsEntity> implements GroupEventsService {

    @NonNull
    MedClinicGroupEventsMapper medCliniCGroupEventsMapper;

    @NonNull
    MedClinicCheckMasterMapper medClinicCheckMasterMapper;

    @NonNull
    PrimaryKeyUtil primaryKeyUtil;

    @NonNull
    MedClinicCheckMasterFunction medClinicCheckMasterFunction;

    @NonNull
    MedClinicCardInfoFunction medClinicCardInfoFunction;

    @NonNull
    PatientSayMapper patientSayMapper;

    @NonNull
    MedClinicVitalsignsMapper medClinicVitalsignsMapper;

    @NonNull
    MedClinicCardInfoMapper medClinicCardInfoMapper;

    @NonNull
    DataDictionaryService dataDictionaryService;

    public static int count = 0;

    /**
     * 新增Or修改
     *
     * @param events 修改条件
     * @return 返回string
     */
    @Override
    public String gesSaveOrUpdate(final GesSaveOrUpdateParamDto events) {

        count = 0;
        final MedClinicGroupEventsEntity medClinicGroupEventsEntity = new MedClinicGroupEventsEntity();
        BeanUtils.copyProperties(events, medClinicGroupEventsEntity);
        if (StringUtils.isEmpty(events.getEventId())) {
            //创建群伤信息
            this.medCliniCGroupEventsMapper.insert(medClinicGroupEventsEntity);
            int peopleNum;
            //根据页面提供对应等级人数创建对应等级患者临时信息
            if (events.getLevel1() > 0) {
                peopleNum = events.getLevel1();
                this.insertCardInfoAndCHeckMaster(peopleNum, CommonConstants.LEVEL_ONE, medClinicGroupEventsEntity);
            }
            if (events.getLevel2() > 0) {
                peopleNum = events.getLevel2();
                this.insertCardInfoAndCHeckMaster(peopleNum, CommonConstants.LEVEL_TWO, medClinicGroupEventsEntity);
            }
            if (events.getLevel3() > 0) {
                peopleNum = events.getLevel3();
                this.insertCardInfoAndCHeckMaster(peopleNum, CommonConstants.LEVEL_THREE, medClinicGroupEventsEntity);
            }
            if (events.getLevel4() > 0) {
                peopleNum = events.getLevel4();
                this.insertCardInfoAndCHeckMaster(peopleNum, CommonConstants.LEVEL_FOUR, medClinicGroupEventsEntity);
            }
        } else {
            this.medCliniCGroupEventsMapper.updateById(medClinicGroupEventsEntity);
        }
        return medClinicGroupEventsEntity.getEventId();
    }

    public void insertCardInfoAndCHeckMaster(final int peopleNum, final String level, final MedClinicGroupEventsEntity medClinicGroupEventsEntity) {

        final List<MedClinicCardInfoEntity> cardInfoEntityList = new ArrayList<>();
        final List<MedClinicCheckMasterEntity> checkMasterList = new ArrayList<>();
        //根据前台提供每个等级人数创建对应患者信息
        for (int i = 0; i < peopleNum; i++) {
            final MedClinicCardInfoEntity cardInfoEntity = new MedClinicCardInfoEntity();
            final MedClinicCheckMasterEntity checkMasterEntity = new MedClinicCheckMasterEntity();
            //患者信息表对象添加数据
            cardInfoEntity.setName(medClinicGroupEventsEntity.getEventName() + count);
            cardInfoEntity.setInfoId(UUID.randomUUID().toString());
            cardInfoEntityList.add(cardInfoEntity);
            //就诊主表对象添加数据
            checkMasterEntity.setRegId(this.primaryKeyUtil.getFlowingWaterId(CommonConstants.MED_CLINIC_CHECK_MASTER));
            checkMasterEntity.setInfoId(cardInfoEntity.getInfoId());
            checkMasterEntity.setEventid(medClinicGroupEventsEntity.getEventId());
            checkMasterEntity.setDiseaseTime(medClinicGroupEventsEntity.getEventTime()); //发病时间
            checkMasterEntity.setAddress(medClinicGroupEventsEntity.getEventAddress());  //发病地址
            checkMasterEntity.setTriageTime(medClinicGroupEventsEntity.getCreateTime()); //分诊时间
            checkMasterEntity.setDepartmentId(medClinicGroupEventsEntity.getClinicDepartmentId()); //就诊科室
            checkMasterEntity.setArriveType(medClinicGroupEventsEntity.getArrayType());
            checkMasterEntity.setSyslevel(level);
            checkMasterEntity.setResetLevel(level);
            checkMasterEntity.setIsUsed(CommonConstants.ISUSED_ENABLE);
            checkMasterEntity.setSubjective(medClinicGroupEventsEntity.getSlefReport()); //添加主诉
            checkMasterEntity.setCreateTime(medClinicGroupEventsEntity.getCreateTime()); //创建时间
            checkMasterList.add(checkMasterEntity);
            count++;
        }
        //生成就诊主表个人信息
        if (cardInfoEntityList.size() > 0 && checkMasterList.size() > 0) {
            this.medClinicCardInfoFunction.saveBatch(cardInfoEntityList);
            this.medClinicCheckMasterFunction.saveBatch(checkMasterList);
        }
    }

    /**
     * 分页查询
     *
     * @param gesDetailsParamDto 查询参数
     * @return 返回分页参数
     */
    @Override
    public IPage<MedClinicGroupEventsEntity> gesSelectPageEvents(final GesDetailsParamDto gesDetailsParamDto) {

        final LambdaQueryWrapper<MedClinicGroupEventsEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(gesDetailsParamDto.getStartTime() != null && gesDetailsParamDto.getEndTime() != null,
                MedClinicGroupEventsEntity::getEventTime, gesDetailsParamDto.getStartTime(),
                gesDetailsParamDto.getEndTime()).eq(StringUtils.isNotBlank(gesDetailsParamDto.getStatus()),
                MedClinicGroupEventsEntity::getStatus, gesDetailsParamDto.getStatus()).orderByAsc(MedClinicGroupEventsEntity::getEventTime);
        final Page<MedClinicGroupEventsEntity> medClinicGroupEventsEntityPage = new Page<>(gesDetailsParamDto.getCurrent(), gesDetailsParamDto.getPageSize());
        return this.medCliniCGroupEventsMapper.selectPage(medClinicGroupEventsEntityPage, queryWrapper);
    }

    /**
     * 通过群伤ID查找相关患者信息
     *
     * @param gesDetailsParamDto 查询参数
     * @return 返回分页参数
     */
    @Override
    public IPage<CardInfoDetailsDto> gesSelectByIdList(final GesDetailsParamDto gesDetailsParamDto) {

        final Page<CardInfoDetailsDto> cardInfoResultDtoPage = new Page<>(gesDetailsParamDto.getCurrent(), gesDetailsParamDto.getPageSize());
        gesDetailsParamDto.setStatus(CommonConstants.ISUSED_ENABLE);
        return this.medCliniCGroupEventsMapper.gesSelectByIdList(cardInfoResultDtoPage, gesDetailsParamDto);
    }

    /**
     * 根据EventId解除关联信息
     *
     * @param ids idList
     * @return 返回boolean
     */
    @Override
    public boolean disassociate(final List<String> ids) {

        if (ids.size() > 0) {
            return this.medCliniCGroupEventsMapper.disassociate(ids);
        }
        return false;
    }

    /**
     * 就诊主表批量绑定群伤事件
     *
     * @param ids     idList
     * @param eventId 群伤事件
     * @return 返回boolean
     */
    @Override
    public boolean bindingCardInfoList(final List<String> ids, final String eventId) {

        boolean flag = false;
        if (ids.size() > 0) {
            flag = this.medClinicCheckMasterMapper.bindingCardInfoList(ids, eventId);
        }
        return flag;
    }

    /**
     * 查询主诉信息
     *
     * @param ids id集合
     * @return 返回list
     */
    @Override
    public List<PatientSayResultDto> selectPatientSay(final List<String> ids) {

        List<PatientSayResultDto> patientSayResultDtos = null;
        if (ids.size() > 0) {
            final List<PatientSayEntity> checkMasterEntityList = this.patientSayMapper.selectList(Wrappers.<PatientSayEntity>lambdaQuery().in(PatientSayEntity::getPatientSayid, ids).eq(PatientSayEntity::getIsValid, CommonConstants.ISUSED_ENABLE));
            patientSayResultDtos = checkMasterEntityList.stream().map(u -> {
                final PatientSayResultDto patientSayResultDto = new PatientSayResultDto();
                patientSayResultDto.setValue(u.getPatientSayid());
                patientSayResultDto.setLabel(u.getDescription());
                return patientSayResultDto;
            }).collect(Collectors.toList());
        }
        return patientSayResultDtos;
    }

    /**
     * 打印分诊凭条
     *
     * @param id 主键id
     * @return 返回boolean
     */
    @Override
    public boolean printCallSlip(final String id, final String templateId, final HttpServletResponse response, final HttpServletRequest request) {

        final LambdaQueryWrapper<MedClinicVitalsignsEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedClinicVitalsignsEntity::getRegId, id).apply("rownum = 1").orderByDesc(MedClinicVitalsignsEntity::getAcqTime);
        //获取相关就诊主表信息
        final MedClinicCheckMasterEntity medClinicCheckMasterEntity = this.medClinicCheckMasterMapper.selectById(id);
        //获取最新的患者评分数据
        MedClinicVitalsignsEntity medClinicVitalsignsEntity = this.medClinicVitalsignsMapper.selectOne(queryWrapper);
        //查询返回的null默认给个空对象
        if (medClinicVitalsignsEntity == null) {
            medClinicVitalsignsEntity = new MedClinicVitalsignsEntity();
        }
        //获取患者数据
        final MedClinicCardInfoEntity medClinicCardInfoEntity = this.medClinicCardInfoMapper.selectById(medClinicCheckMasterEntity.getInfoId());
        if (medClinicCardInfoEntity.getPatientId() == null) {
            log.info("病历号不能为空");
            return false;
        }
        final String path = Objects.requireNonNull(this.getClass().getResourceAsStream("/")).toString();//系统路径
        //获取模板数据
        final List<TemplateEntity> templateEntities = this.medCliniCGroupEventsMapper.getTemplateList(templateId);
        final TemplateEntity templateEntity1 = templateEntities.stream().filter(t -> "graphics2D".equals(t.getContent())).findAny().orElse(null);

        final String pngPathFile = path + CommonConstants.TRIAGE_GENETATE + id + CommonConstants.PNG;
        try {
            final Field[] cardInfoFields = medClinicCardInfoEntity.getClass().getDeclaredFields();
            final Field[] checkMasterFields = medClinicCheckMasterEntity.getClass().getDeclaredFields();
            final Field[] vitalsignsFields = medClinicVitalsignsEntity.getClass().getDeclaredFields();
            for (final TemplateEntity entity : templateEntities) {
                StringBuilder sb = new StringBuilder();
                sb.append(entity.getContent());
                //根据模板匹配实际字段信息
                if (entity.getContentValue() != null) {
                    sb = this.getValue(sb, cardInfoFields, entity, medClinicCardInfoEntity);
                    sb = this.getValue(sb, checkMasterFields, entity, medClinicCheckMasterEntity);
                    sb = this.getValue(sb, vitalsignsFields, entity, medClinicVitalsignsEntity);
                }
                entity.setContent(sb.toString());
            }
            TemplateUtil.imageBuilder(medClinicCardInfoEntity.getPatientId(), pngPathFile, "2", templateEntities, templateEntity1);
            ExcelToPdfUtil.buildPngDocument(pngPathFile, response);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.deleteFile(pngPathFile);
        }
        return false;
    }

    /**
     * 打印腕带
     *
     * @param id 打印regID
     * @return 返回boolean
     */
    @Override
    public boolean printWristBands(final String id, final String templateId, final HttpServletResponse response, final HttpServletRequest request) {

        final MedClinicCheckMasterEntity medClinicCheckMasterEntity = this.medClinicCheckMasterMapper.selectById(id);
        final MedClinicCardInfoEntity medClinicCardInfoEntity = this.medClinicCardInfoMapper.selectById(medClinicCheckMasterEntity.getInfoId());

        final String path = Objects.requireNonNull(this.getClass().getResourceAsStream("/")).toString();//系统路径
        //获取模板数据
        final List<TemplateEntity> templateEntities = this.medCliniCGroupEventsMapper.getTemplateList(templateId);
        final TemplateEntity templateEntity1 = templateEntities.stream().filter(t -> "graphics2D".equals(t.getContent())).findAny().orElse(null);

        final String pngPathFile = path + CommonConstants.WRISTBANDS_GENETATE + id + CommonConstants.PNG;
        try {
            final Field[] cardInfoFields = medClinicCardInfoEntity.getClass().getDeclaredFields();
            final Field[] checkMasterFields = medClinicCheckMasterEntity.getClass().getDeclaredFields();
            for (final TemplateEntity entity : templateEntities) {
                StringBuilder sb = new StringBuilder();
                sb.append(entity.getContent());
                //根据模板匹配实际字段信息
                if (entity.getContentValue() != null) {
                    sb = this.getValue(sb, cardInfoFields, entity, medClinicCardInfoEntity);
                    sb = this.getValue(sb, checkMasterFields, entity, medClinicCheckMasterEntity);
                }
                entity.setContent(sb.toString());
            }
            TemplateUtil.imageBuilder(medClinicCardInfoEntity.getPatientId(), pngPathFile, "1", templateEntities, templateEntity1);
            ExcelToPdfUtil.buildPngDocument(pngPathFile, response);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.deleteFile(pngPathFile);
        }
        return false;
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
    public <T> StringBuilder getValue(final StringBuilder sb, final Field[] fields, final TemplateEntity entity, final T tClass) {

        String value;
        try {
            for (final Field field : fields) {
                if (entity.getContentValue().equals(field.getName())) {
                    field.setAccessible(true);
                    if (field.get(tClass) != null) {
                        value = String.valueOf(field.get(tClass));
                        value = this.matchingValue(entity.getContentValue(), value);
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

    /**
     * 导出List查询
     *
     * @param gesDetailsParamDto 卖出查询条件参数
     * @return 返回boolean
     */
    @Override
    public boolean exportExcel(final GesDetailsParamDto gesDetailsParamDto, final HttpServletResponse response, final HttpServletRequest request) {

        final List<GroupEventsExportResultDto> groupEventsResultDtoList = this.medCliniCGroupEventsMapper.selectByKey(gesDetailsParamDto);
        final InputStream is;
        try {
            is = this.getClass().getResourceAsStream(CommonConstants.GROUPEVENTS_EXCEL_URL);
            if (is == null) {
                log.info("groupEventsExcel，xls模板不存在");
                return false;
            }
            //读取excel模板
            final HSSFWorkbook wb = new HSSFWorkbook(is);
            // 新建的Sheet页
            HSSFSheet newSheet;
            // 创建行
            HSSFRow row;

            for (int i = 0; i < groupEventsResultDtoList.size(); i++) {
                final GroupEventsExportResultDto groupEventsExportResultDto = groupEventsResultDtoList.get(i);
                //读取了模板内所有sheet内容
                // 设置新建Sheet的页名
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(Date.from(groupEventsExportResultDto.getEventTime().atZone(ZoneId.systemDefault()).toInstant()));
                final String sheetName = groupEventsExportResultDto.getEventName() +
                        calendar.get(Calendar.DAY_OF_MONTH) + "日" +
                        calendar.get(Calendar.HOUR_OF_DAY) + "时";
                // 将模板中的内容复制到新建的Sheet页中
                newSheet = wb.cloneSheet(0);
                wb.setSheetName(i + 1, sheetName);
                //获取到新建Sheet页中的第一行为其中的列赋值
                row = ExcelUtils.getRow(newSheet, 1);
                ExcelUtils.setCellValue(row, 2, groupEventsExportResultDto.getEventName()); //事件名称
                row = ExcelUtils.getRow(newSheet, 2);
                ExcelUtils.setCellValue(row, 2, Date.from(groupEventsExportResultDto.getEventTime().atZone(ZoneId.systemDefault()).toInstant())); //发生时间
                ExcelUtils.setCellValue(row, 4, groupEventsExportResultDto.getEventAddress()); //发生地点
                final String eventProperty = this.matching(groupEventsExportResultDto.getEventProperty(), DictionaryEnum.PIAT_0012.getValue());
                ExcelUtils.setCellValue(row, 8, eventProperty); //事件性质
                row = ExcelUtils.getRow(newSheet, 3);
                ExcelUtils.setCellValue(row, 2, groupEventsExportResultDto.getDescribe());
                row = ExcelUtils.getRow(newSheet, 4);
                ExcelUtils.setCellValue(row, 2, groupEventsExportResultDto.getMeasures());

                final List<CardInfoExportResultDto> cardInfoExportResultDtoList = groupEventsExportResultDto.getCardInfoExportResultDtoList();
                for (int j = 0; j < cardInfoExportResultDtoList.size(); j++) {
                    final CardInfoExportResultDto cardInfoExportResultDto = cardInfoExportResultDtoList.get(j);
                    final int index = 7 + j;
                    row = ExcelUtils.getRow(newSheet, index);
                    if (cardInfoExportResultDto != null) {
                        ExcelUtils.setCellValue(row, 0, j);  //序列号
                        ExcelUtils.setCellValue(row, 1, cardInfoExportResultDto.getPatientId());
                        ExcelUtils.setCellValue(row, 2, cardInfoExportResultDto.getRegId());
                        ExcelUtils.setCellValue(row, 3, cardInfoExportResultDto.getName());
                        ExcelUtils.setCellValue(row, 4, cardInfoExportResultDto.getRegAge()); //年龄

                        //性别
                        final String gender = this.matching(cardInfoExportResultDto.getGender(), DictionaryEnum.PIAT_0001.getValue());
                        ExcelUtils.setCellValue(row, 5, gender);
                        //分诊级别
                        final String syslevel = this.matching(cardInfoExportResultDto.getResetLevel(), DictionaryEnum.PIAT_0009.getValue());
                        ExcelUtils.setCellValue(row, 6, syslevel);
                        ExcelUtils.setCellValue(row, 7, cardInfoExportResultDto.getDiagnoseDescribe());
                        //去向
                        final String turnResult = this.matching(cardInfoExportResultDto.getTurnResult(), DictionaryEnum.PIAT_0008.getValue());
                        ExcelUtils.setCellValue(row, 8, turnResult);
                    }
                }
            }
            wb.removeSheetAt(0);
//            修改模板内容导出新模板
            ExcelUtils.buildExcelDocument("群伤事件.xls", wb, response);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改状态 && 解决相关患者关系
     *
     * @param ids id集合
     * @return 返回boolean
     */
    @Override
    public boolean updateStatus(final List<String> ids, final String status) {

        boolean flag = false;
        final List<MedClinicGroupEventsEntity> eventsList = this.medCliniCGroupEventsMapper.selectList(Wrappers.<MedClinicGroupEventsEntity>lambdaQuery().in(MedClinicGroupEventsEntity::getEventId, ids));
        final List<MedClinicGroupEventsEntity> groupEventsEntities = eventsList.stream().peek(medClinicGroupEventsEntity -> medClinicGroupEventsEntity.setStatus(status)).collect(Collectors.toList());
        if (groupEventsEntities.size() > 0) {
            //解除就诊主表中对应的关系
            this.medCliniCGroupEventsMapper.disassociateByEventId(ids);
            //对群伤事件作删除处理
            flag = this.updateBatchById(groupEventsEntities);
        }
        return flag;
    }

    /**
     * 患者列表
     */
    @Override
    public IPage<RegitionListsResultDto> selectByCardInfo(final RegitionListsParamDto regListsParamDto) {

        //处理模糊查询字段
        regListsParamDto.setSearchValue(HandleString.repString(regListsParamDto.getSearchValue()));
        final Page<RegitionListsResultDto> page = new Page<>(regListsParamDto.getCurrent(), regListsParamDto.getPageSize());
        return this.medCliniCGroupEventsMapper.regList(regListsParamDto, page);
    }

    /**
     * 匹配字典表中中文
     *
     * @return
     */
    public String matchingValue(final String key, final String value) {

        switch (key) {
            case "PIAT_0001":
            case "PIAT_0009":
            case "PIAT_0014":
                return this.matching(value, key);
            default:
                return value;
        }
    }

    /**
     * 字典匹配
     *
     * @param result 需要修改的字段
     * @param param  字典表中标识
     * @return 返回string
     */
    public String matching(String result, final String param) {
        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0001.getValue(), DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0014.getValue());
        //性别
        final List<MedDictionaryEntity> drDtoList = dictByCode.get(param);
        if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(result)) {
            for (final MedDictionaryEntity drDto : drDtoList) {
                if (result.equals(drDto.getItemcode())) {
                    result = drDto.getItemname();
                }
            }
        }
        return result;
    }
}
