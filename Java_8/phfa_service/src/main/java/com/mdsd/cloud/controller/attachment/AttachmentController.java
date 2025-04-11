package com.mdsd.cloud.controller.attachment;

import com.mdsd.cloud.controller.attachment.dto.AttListOrDetailPao;
import com.mdsd.cloud.controller.attachment.dto.AttSavePao;
import com.mdsd.cloud.controller.attachment.service.AttachmentService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 附件
 *
 * @author WangYunwei [2022-04-11]
 */
@RestController
@RequestMapping("/AttachmentController/v1")
@Api(tags = "附件", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
public class AttachmentController {

    AttachmentService attachmentService;

    public AttachmentController(final AttachmentService attachmentService) {

        this.attachmentService = attachmentService;
    }

    /**
     * 附件 - 保存
     *
     * @param multipartFile 附件
     * @param businessId    业务ID
     * @param fileType      文件类型(注:字典表中的文件类型值)
     * @param remark        备注
     */
    @ApiOperation(value = "附件 - 保存")
    @PostMapping(name = "保存", value = "/attSave")
    public ResponseDto<String> attSave(@RequestParam(value = "file") final MultipartFile multipartFile, @RequestParam(value = "requestPath") final String requestPath, @RequestParam(value = "businessId") final String businessId, @RequestParam(value = "fileType", required = false) final Integer fileType, @RequestParam(value = "remark", required = false) final String remark) {

        final AttSavePao attSavePao = new AttSavePao().setRequestPath(requestPath).setBusinessId(businessId).setFileType(fileType).setRemark(remark);
        return ResponseUtil.wrapSuccess(this.attachmentService.attSave(multipartFile, attSavePao));
    }

    /**
     * 附件 - 删除
     *
     * @param ids 附件ID集合
     */
    @ApiOperation(value = "附件 - 删除")
    @PostMapping(name = "删除", value = "/attDelete")
    public ResponseDto<Boolean> attDelete(@RequestBody final List<Long> ids) {

        return ResponseUtil.wrapSuccess(this.attachmentService.attDelete(ids));
    }

    /**
     * 附件 - 列表or详情
     */
    @ApiOperation(value = "附件 - 列表or详情")
    @PostMapping(name = "列表or详情", value = "/attListOrDetail")
    public ResponseDto<?> attListOrDetail(@RequestBody final AttListOrDetailPao attListOrDetailPao) {

        return ResponseUtil.wrapSuccess(this.attachmentService.attListOrDetail(attListOrDetailPao));
    }
}
