package com.mdsd.cloud.controller.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mdsd.cloud.controller.notice.service.ClinicCheckMasterService;
import com.mdsd.cloud.controller.registration.entity.MedClinicCheckMasterEntity;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCheckMasterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wgy
 **/
@Service
@RequiredArgsConstructor
public class ClinicCheckMasterServiceImpl implements ClinicCheckMasterService {

    private final MedClinicCheckMasterMapper medClinicCheckMasterMapper;

    @Override
    public Boolean checkMaster(final String phepPid) {

        final LambdaQueryWrapper<MedClinicCheckMasterEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedClinicCheckMasterEntity::getPhepPid, phepPid);

        return CollectionUtils.isNotEmpty(this.medClinicCheckMasterMapper.selectList(queryWrapper));
    }
}
