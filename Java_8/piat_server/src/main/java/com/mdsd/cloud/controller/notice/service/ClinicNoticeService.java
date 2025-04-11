package com.mdsd.cloud.controller.notice.service;

import com.mdsd.cloud.controller.notice.dto.ClinicNoticeRequest;
import com.mdsd.cloud.controller.notice.dto.ClinicNoticeVo;
import com.mdsd.cloud.controller.notice.entity.ClinicNoticeEntity;

import java.util.List;

/**
 * @description :二次分诊
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/

public interface ClinicNoticeService {

    void save(ClinicNoticeEntity clinicNoticeEntity);

    void update(ClinicNoticeEntity clinicNoticeEntity);

    void deleteById(String id);

    List<ClinicNoticeVo> queryList(ClinicNoticeRequest clinicNoticeRequest);
}
