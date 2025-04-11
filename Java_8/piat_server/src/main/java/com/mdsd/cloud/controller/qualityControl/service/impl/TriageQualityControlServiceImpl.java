package com.mdsd.cloud.controller.qualityControl.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.qualityControl.Vo.*;
import com.mdsd.cloud.controller.qualityControl.mapper.TriageQualityControlMapper;
import com.mdsd.cloud.controller.qualityControl.service.TriageQualityControlService;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huxiaoneng
 */
@Service
@RequiredArgsConstructor
public class TriageQualityControlServiceImpl implements TriageQualityControlService {

    private final TriageQualityControlMapper triageQualityControlMapper;

    @NonNull
    DataDictionaryService dataDictionaryService;

    /**
     * 急诊各级患者查询
     */
    @Override
    public Map<String, Object> queryPatientProportionReport(final PatientProportionReportRequestVo patientProportionReportRequestVo) {

        final Map<String, Object> patientProportionMap = new HashMap<>();
        List<PatientProportionReportVo> patientProportionReportVos = this.triageQualityControlMapper.queryPatientProportionReport(patientProportionReportRequestVo);
        final int count = patientProportionReportVos.stream().mapToInt(PatientProportionReportVo::getPeopleNumber).sum();
        final DecimalFormat format = new DecimalFormat("0.00");
        if (count > 0) {
            patientProportionReportVos = patientProportionReportVos.stream().map(detailVo -> {
                final PatientProportionReportVo vo = new PatientProportionReportVo();
                BeanUtils.copyProperties(detailVo, vo);
                vo.setProportion(format.format((float) vo.getPeopleNumber() / count * 100));
                return vo;
            }).collect(Collectors.toList());
        }
        patientProportionMap.put("patientProportionReportVos", patientProportionReportVos);
        patientProportionMap.put("count", count);
        return patientProportionMap;
    }

    /**
     * 急诊各级患者明细查询
     */
    @Override
    public IPage<PatientProportionDetailReportVo> queryPatientProportionDetailReport(final PatientProportionReportRequestVo patientProportionReportRequestVo) {

        final Page<PatientProportionDetailReportVo> page = new Page<>(patientProportionReportRequestVo.getCurrent(), patientProportionReportRequestVo.getPageSize());

        final IPage<PatientProportionDetailReportVo> patientProportionDetailReportVoIPage = this.triageQualityControlMapper.queryPatientProportionDetailReport(page, patientProportionReportRequestVo);
        return patientProportionDetailReportVoIPage;
    }

    /**
     * 急诊各级患者比例查询，月度，年度
     */
    @Override
    public Map<String, Object> queryMonthProportionReport(final PatientProportionReportRequestVo patientProportionReportRequestVo, final String status) {

        final Map<String, Object> map = new HashMap<>();
        List<PatientMonthProportionReportVo> patientVos = new ArrayList<>();
        List<PatientMonthProportionReportChartsVo> patientChartsVos = new ArrayList<>();
        if (status.equals("1")) {
            patientVos = this.triageQualityControlMapper.queryMonthProportionReport(patientProportionReportRequestVo);
            patientChartsVos = this.triageQualityControlMapper.queryMonthProportionReportCharts(patientProportionReportRequestVo);
        } else if (status.equals("2")) {
            patientVos = this.triageQualityControlMapper.queryYearProportionReport(patientProportionReportRequestVo);
            patientChartsVos = this.triageQualityControlMapper.queryYearProportionReportCharts(patientProportionReportRequestVo);
        }
        final DecimalFormat format = new DecimalFormat("0.00");
        patientVos = patientVos.stream().map(detailVo -> {
            final PatientMonthProportionReportVo vo = new PatientMonthProportionReportVo();
            BeanUtils.copyProperties(detailVo, vo);
            final int count = vo.getAPnumber() + vo.getBPnumber() + vo.getCPnumber() + vo.getDPnumber();
            if (count > 0) {
                vo.setAProportion(format.format((float) vo.getAPnumber() / count * 100));
                vo.setBProportion(format.format((float) vo.getBPnumber() / count * 100));
                vo.setCProportion(format.format((float) vo.getCPnumber() / count * 100));
                vo.setDProportion(format.format((float) vo.getDPnumber() / count * 100));
            }
            vo.setPatientCount(count);
            return vo;
        }).collect(Collectors.toList());
        map.put("patientChartsVos", patientChartsVos);
        map.put("patientVos", patientVos);
        return map;

    }

    /**
     * 个人登记 - 列表_导出
     */
    @Override
    public List<PatientProportionDetailReportVo> patientDetailExport(final PatientProportionReportRequestVo patientProportionReportRequestVo) {
        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0001.getValue(), DictionaryEnum.PIAT_0009.getValue(), DictionaryEnum.PIAT_0014.getValue());
        //查询数据
        List<PatientProportionDetailReportVo> patientProportionDetailReportVos = this.triageQualityControlMapper.queryPatientProportionDetailReport(patientProportionReportRequestVo);
        patientProportionDetailReportVos = patientProportionDetailReportVos.stream().map(detailVo -> {
            final PatientProportionDetailReportVo vo = new PatientProportionDetailReportVo();
            BeanUtils.copyProperties(detailVo, vo);
            vo.setGender(this.matching(vo.getGender(), DictionaryEnum.PIAT_0001.getValue(), dictByCode));
            vo.setResetLevel(this.matching(vo.getResetLevel(), DictionaryEnum.PIAT_0009.getValue(), dictByCode));
            vo.setDepartmentId(this.matching(vo.getDepartmentId(), DictionaryEnum.PIAT_0014.getValue(), dictByCode));
            return vo;
        }).collect(Collectors.toList());

        return patientProportionDetailReportVos;
    }

    /**
     * 分诊正确率查询
     */
    @Override
    public Map<String, Object> queryTriageWrongRatio(final TriageWrongRequestVo triageWrongRequestVo) {
        //查询字典数据
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PIAT_0017.getValue());
        //查询数据
        final Map<String, Object> triageQualityMap = new HashMap<>();
        List<TriageWrongRatioVo> ratioVos = this.triageQualityControlMapper.queryTriageWrongRatio(triageWrongRequestVo);
        final int triageQualityCount = this.triageQualityControlMapper.queryTriageWrongRatioCount(triageWrongRequestVo);
        final int patientCount = this.triageQualityControlMapper.queryPatientCount(triageWrongRequestVo);
        final TriageWrongRatioVo wrongRatioVo = new TriageWrongRatioVo();
        wrongRatioVo.setErrType("错误总数");
        wrongRatioVo.setCount(triageQualityCount);
        ratioVos.add(wrongRatioVo);
        final DecimalFormat format = new DecimalFormat("0.00");
        ratioVos = ratioVos.stream().map(detailVo -> {
            final TriageWrongRatioVo vo = new TriageWrongRatioVo();
            BeanUtils.copyProperties(detailVo, vo);
            if (patientCount > 0) {
                vo.setRatio(format.format((float) vo.getCount() / patientCount * 100));
            }
            vo.setErrType(this.matching(vo.getErrType(), DictionaryEnum.PIAT_0017.getValue(), dictByCode));
            return vo;
        }).collect(Collectors.toList());
        triageQualityMap.put("triageWrongRatioVo", ratioVos);
        triageQualityMap.put("patientCount", patientCount);
        return triageQualityMap;
    }

    /**
     * 查询分诊正确率详情
     */
    @Override
    public IPage<TriageWrongDetailVo> queryTriageWrongRatioDetail(final TriageWrongRequestVo triageWrongRequestVo) {

        final Page<TriageWrongDetailVo> page = new Page<>(triageWrongRequestVo.getCurrent(), triageWrongRequestVo.getPageSize());
        final IPage<TriageWrongDetailVo> detailVos = this.triageQualityControlMapper.queryTriageWrongRatioDetail(page, triageWrongRequestVo);
        return detailVos;
    }

    /**
     * 查询分诊正确率月度
     */
    @Override
    public Map<String, Object> queryTriageWrongMonthRatio(final TriageWrongRequestVo triageWrongRequestVo, final String status) {

        final Map<String, Object> map = new HashMap<>();
        List<TriageWrongMonthRatioVo> triageWrongRatioVos = new ArrayList<>();
        List<TriageWrongRatioChartsVo> ratioChartsVos = new ArrayList<>();
        List<TriageWrongRatioChartsVo> ratioChartsCountVos = new ArrayList<>();
        //status:1是月度查询，2是年度查询
        if (status.equals("1")) {
            triageWrongRatioVos = this.triageQualityControlMapper.queryTriageWrongMonthRatio(triageWrongRequestVo);
            ratioChartsVos = this.triageQualityControlMapper.queryTriageWrongMonthCharts(triageWrongRequestVo);
            ratioChartsCountVos = this.triageQualityControlMapper.queryTriageWrongMonthChartsCount(triageWrongRequestVo);
        } else if (status.equals("2")) {
            triageWrongRatioVos = this.triageQualityControlMapper.queryTriageWrongYearRatio(triageWrongRequestVo);
            ratioChartsVos = this.triageQualityControlMapper.queryTriageWrongYearCharts(triageWrongRequestVo);
            ratioChartsCountVos = this.triageQualityControlMapper.queryTriageWrongYearChartsCount(triageWrongRequestVo);
        }
        ratioChartsVos.addAll(ratioChartsCountVos);
        final DecimalFormat format = new DecimalFormat("0.00");
        triageWrongRatioVos = triageWrongRatioVos.stream().map(detailVo -> {
            final TriageWrongMonthRatioVo vo = new TriageWrongMonthRatioVo();
            BeanUtils.copyProperties(detailVo, vo);
            if (vo.getPatientCount() > 0) {
                vo.setARatio(format.format((float) vo.getACount() / vo.getPatientCount() * 100));
                vo.setBRatio(format.format((float) vo.getBCount() / vo.getPatientCount() * 100));
                vo.setCRatio(format.format((float) vo.getCCount() / vo.getPatientCount() * 100));
            }
            return vo;
        }).collect(Collectors.toList());
        map.put("triageWrongRatioVos", triageWrongRatioVos);
        map.put("ratioChartsVos", ratioChartsVos);
        return map;
    }

    /**
     * 分诊正确率导出查询
     */
    @Override
    public List<TriageWrongDetailVo> queryTriageWrongDetailExport(final TriageWrongRequestVo triageWrongRequestVo) {

        final List<TriageWrongDetailVo> triageWrongDetailVos = this.triageQualityControlMapper.queryTriageWrongRatioDetail(triageWrongRequestVo);
        return triageWrongDetailVos;
    }

    /**
     * 字典匹配
     *
     * @param result 需要修改的字段
     * @param param  字典表中标识
     * @return 返回string
     */
    public String matching(String result, final String param, final Map<String, List<MedDictionaryEntity>> dictByCode) {
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
