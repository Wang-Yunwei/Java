package com.mdsd.cloud.controller.attachment.service;

import com.mdsd.cloud.controller.attachment.dto.AttListOrDetailPao;
import com.mdsd.cloud.controller.attachment.dto.AttSavePao;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangYunwei [2022-04-11]
 */
public interface AttachmentService {

    /**
     * 附件 - 保存
     */
    String attSave(MultipartFile multipartFile, AttSavePao attSavePao);

    /**
     * 附件 - 删除
     */
    boolean attDelete(List<Long> ids);

    /**
     * 附件 - 列表or详情
     */
    Object attListOrDetail(AttListOrDetailPao attListOrDetailPao);
}
