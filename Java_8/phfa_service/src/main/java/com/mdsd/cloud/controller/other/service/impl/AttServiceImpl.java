package com.mdsd.cloud.controller.other.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.attachment.mapper.MedPhepAttachmentMapper;
import com.mdsd.cloud.controller.other.dto.AttachmentDto;
import com.mdsd.cloud.controller.other.service.AttService;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.enumeration.ValidEnum;
import com.mdsd.cloud.mapper.MedPhepPatientsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaoneng.h
 * @date 2023/7/26 14:55
 */
@Service
@RequiredArgsConstructor
public class AttServiceImpl implements AttService {

    private final MedPhepAttachmentMapper attachmentMapper;

    private final MedPhepPatientsMapper patientsMapper;

    /**
     * 获取附件信息详情
     * @param missionId 业务id
     * @return AttachmentDto
     */
    @Override
    public List<AttachmentDto> getAtt(String missionId) {

        final MedPhepPatientsEntity medPhepPatientsEntity = this.patientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery()
                .eq(MedPhepPatientsEntity::getMissionId, missionId));
        List<AttachmentDto> attachmentDtos = new ArrayList<>();
        if (!Objects.isNull(medPhepPatientsEntity)) {
            final List<MedPhepAttachmentEntity> attachmentEntities = this.attachmentMapper.selectList(Wrappers.<MedPhepAttachmentEntity>lambdaQuery()
                    .eq(MedPhepAttachmentEntity::getBusinessId, medPhepPatientsEntity.getPatientId())
                    .eq(MedPhepAttachmentEntity::getDeleteFlag, ValidEnum.INVALID.getValue()));
            attachmentEntities.forEach(item -> {
                AttachmentDto attachmentDto = new AttachmentDto();
                attachmentDto.setId(item.getId());
                attachmentDto.setBusinessId(item.getBusinessId());
                attachmentDto.setType(item.getFileType());
                attachmentDto.setRemark(item.getRemark());
                attachmentDto.setAttachPath(item.getRequestPath());
                attachmentDtos.add(attachmentDto);
            });
        }
        return attachmentDtos;
    }
}
