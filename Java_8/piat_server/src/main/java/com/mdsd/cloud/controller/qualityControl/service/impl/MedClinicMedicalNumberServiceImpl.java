package com.mdsd.cloud.controller.qualityControl.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationDetailVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationNumberRequestVo;
import com.mdsd.cloud.controller.qualityControl.Vo.DoctorPatientRationRequestVo;
import com.mdsd.cloud.controller.qualityControl.entity.MedClinicMedicalNumberEntity;
import com.mdsd.cloud.controller.qualityControl.mapper.MedClinicMedicalNumberMapper;
import com.mdsd.cloud.controller.qualityControl.service.MedClinicMedicalNumberService;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MedClinicMedicalNumberServiceImpl implements MedClinicMedicalNumberService {

    private final MedClinicMedicalNumberMapper medClinicMedicalNumberMapper;

    @Override
    public DoctorPatientRationDetailVo queryByArea(final DoctorPatientRationRequestVo doctorPatientRationRequestVo) {

        final DoctorPatientRationDetailVo detailVo = new DoctorPatientRationDetailVo();
        final DecimalFormat format = new DecimalFormat("0.00000");
        final LambdaQueryWrapper<MedClinicMedicalNumberEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedClinicMedicalNumberEntity::getStatus, CommonConstants.ISUSED_ENABLE)
                .eq(MedClinicMedicalNumberEntity::getHospitalArea, doctorPatientRationRequestVo.getHospitalArea())
                .eq(MedClinicMedicalNumberEntity::getYear, doctorPatientRationRequestVo.getYear());
        final MedClinicMedicalNumberEntity medClinicMedicalNumberEntity = this.medClinicMedicalNumberMapper.selectOne(queryWrapper);
        final String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        if (medClinicMedicalNumberEntity == null) {
            final int patientCount = this.medClinicMedicalNumberMapper.selectPatientCount(doctorPatientRationRequestVo);
            final MedClinicMedicalNumberEntity medicalNumberEntity = new MedClinicMedicalNumberEntity();
            medicalNumberEntity.setYear(doctorPatientRationRequestVo.getYear());
            medicalNumberEntity.setHospitalArea(doctorPatientRationRequestVo.getHospitalArea());
            medicalNumberEntity.setPatientNumber(patientCount);
            medicalNumberEntity.setStatus(CommonConstants.ISUSED_ENABLE);
            this.medClinicMedicalNumberMapper.insert(medicalNumberEntity);
            BeanUtils.copyProperties(medicalNumberEntity, detailVo);
        } else if (medClinicMedicalNumberEntity.getYear().equals(year)) {
            final int patientCount = this.medClinicMedicalNumberMapper.selectPatientCount(doctorPatientRationRequestVo);
            medClinicMedicalNumberEntity.setPatientNumber(patientCount);
            this.medClinicMedicalNumberMapper.updateById(medClinicMedicalNumberEntity);
            BeanUtils.copyProperties(medClinicMedicalNumberEntity, detailVo);
        } else {
            BeanUtils.copyProperties(medClinicMedicalNumberEntity, detailVo);
        }
        if (detailVo.getPatientNumber() != 0) {
            detailVo.setDoctorPatientRatio(format.format(detailVo.getDoctorNumber() / detailVo.getPatientNumber()));
            detailVo.setNursePatientRatio(format.format(detailVo.getNurseNumber() / detailVo.getPatientNumber()));
        }
        return detailVo;
    }

    @Override
    public IPage<DoctorPatientRationDetailVo> queryList(final DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo) {

        final Page<DoctorPatientRationDetailVo> page = new Page<>(doctorPatientRationNumberRequestVo.getCurrent(), doctorPatientRationNumberRequestVo.getPageSize());
        final IPage<DoctorPatientRationDetailVo> detailVos = this.medClinicMedicalNumberMapper.queryList(page, doctorPatientRationNumberRequestVo);
        return detailVos;
    }

    @Override
    public Boolean saveOrUpdateByKey(final MedClinicMedicalNumberEntity medClinicMedicalNumberEntity) {

        final LambdaQueryWrapper<MedClinicMedicalNumberEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MedClinicMedicalNumberEntity::getStatus, CommonConstants.ISUSED_ENABLE)
                .eq(MedClinicMedicalNumberEntity::getHospitalArea, medClinicMedicalNumberEntity.getHospitalArea())
                .eq(MedClinicMedicalNumberEntity::getYear, medClinicMedicalNumberEntity.getYear());
        final MedClinicMedicalNumberEntity numberEntity = this.medClinicMedicalNumberMapper.selectOne(queryWrapper);
        if (medClinicMedicalNumberEntity.getId() != null) {

            return this.medClinicMedicalNumberMapper.updateById(medClinicMedicalNumberEntity) > 0;
        } else {
            if (numberEntity != null) {
                throw new RuntimeException("本院区该年度已经存在相应数据，请选择其他院区或者年份操作");
            } else {
                medClinicMedicalNumberEntity.setStatus("1");
                return this.medClinicMedicalNumberMapper.insert(medClinicMedicalNumberEntity) > 0;
            }
        }
    }

    @Override
    public void detailExport(final DoctorPatientRationNumberRequestVo doctorPatientRationNumberRequestVo, final HttpServletResponse response) {

        //查询数据
        List<DoctorPatientRationDetailVo> detailVos = this.medClinicMedicalNumberMapper.queryList(doctorPatientRationNumberRequestVo);
        detailVos = detailVos.stream().map(detailVo -> {
            final DoctorPatientRationDetailVo vo = new DoctorPatientRationDetailVo();
            BeanUtils.copyProperties(detailVo, vo);
            if (vo.getDoctorNumber() > 0) {
                final int rint = (int) Math.rint(vo.getPatientNumber() / vo.getDoctorNumber());
                vo.setDoctorPatientRatio("1:" + rint);
            } else {
                vo.setDoctorPatientRatio("");
            }
            if (vo.getNurseNumber() > 0) {
                final int rint = (int) Math.rint(vo.getPatientNumber() / vo.getNurseNumber());
                vo.setNursePatientRatio("1:" + rint);
            } else {
                vo.setNursePatientRatio("");
            }
            return vo;
        }).collect(Collectors.toList());
        if (detailVos.size() > 0) {
            try {
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                final String fileName = URLEncoder.encode("急诊科医护患比.xlsx", "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                final InputStream resourceAsStream = this.getClass().getResourceAsStream("/excel-templates/doctorPatientRationExcelTemplate.xlsx");
                EasyExcel.write(response.getOutputStream()).withTemplate(resourceAsStream).sheet().doFill(detailVos);
            } catch (final IOException e) {
                throw new BusinessException("Excel导出失败");
            }
        }
    }

}
