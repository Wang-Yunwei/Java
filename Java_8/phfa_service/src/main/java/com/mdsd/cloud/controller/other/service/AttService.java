package com.mdsd.cloud.controller.other.service;

import com.mdsd.cloud.controller.other.dto.AttachmentDto;

import java.util.List;

/**
 * @author xiaoneng.h
 * @date 2023/7/26 14:55
 */
public interface AttService {

    /**
     * 获取附件信息详情
     * @param missionId id
     * @return attachmentDto
     */
    List<AttachmentDto> getAtt(String missionId);
}
