package com.mdsd.cloud.controller.notice.service.impl;

import com.mdsd.cloud.controller.notice.dto.ClinicNoticeRequest;
import com.mdsd.cloud.controller.notice.dto.ClinicNoticeVo;
import com.mdsd.cloud.controller.notice.entity.ClinicNoticeEntity;
import com.mdsd.cloud.controller.notice.mapper.ClinicNoticeMapper;
import com.mdsd.cloud.controller.notice.service.ClinicNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description :通知
 * @author: wgy
 * @create: 2021-09-18 14:06
 **/

@Service
@RequiredArgsConstructor
public class ClinicNoticeServiceImpl implements ClinicNoticeService {

    private final ClinicNoticeMapper clinicNoticeMapper;

    @Override
    public void save(ClinicNoticeEntity medPhepTemplateEntity) {
        clinicNoticeMapper.insert(medPhepTemplateEntity);
    }

    @Override
    public void update(ClinicNoticeEntity medPhepTemplateEntity) {
        clinicNoticeMapper.updateById(medPhepTemplateEntity);
    }

    @Override
    public void deleteById(String id) {
        clinicNoticeMapper.deleteById(id);
    }

    @Override
    public List<ClinicNoticeVo> queryList(ClinicNoticeRequest clinicNoticeRequest) {
        return clinicNoticeMapper.queryList(clinicNoticeRequest);
    }
}
