package com.mdsd.cloud.controller.groupevents.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdsd.cloud.controller.groupevents.dto.*;
import com.mdsd.cloud.controller.groupevents.entity.MedClinicGroupEventsEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/13 16:26
 */
public interface GroupEventsService extends IService<MedClinicGroupEventsEntity> {

    String gesSaveOrUpdate(GesSaveOrUpdateParamDto events);

    boolean exportExcel(GesDetailsParamDto gesDetailsParamDto, HttpServletResponse response, HttpServletRequest request);

    boolean updateStatus(List<String> ids, String status);

    IPage<MedClinicGroupEventsEntity> gesSelectPageEvents(GesDetailsParamDto gesDetailsParamDto);

    IPage<CardInfoDetailsDto> gesSelectByIdList(GesDetailsParamDto gesDetailsParamDto);

    boolean disassociate(List<String> ids);

    boolean bindingCardInfoList(List<String> ids, String eventId);

    List<PatientSayResultDto> selectPatientSay(List<String> ids);

    boolean printCallSlip(String id,String templateId, HttpServletResponse response, HttpServletRequest request);

    boolean printWristBands(String id, String templateId, HttpServletResponse response, HttpServletRequest request);

    IPage<RegitionListsResultDto> selectByCardInfo(RegitionListsParamDto regitionListsParamDto);
}
