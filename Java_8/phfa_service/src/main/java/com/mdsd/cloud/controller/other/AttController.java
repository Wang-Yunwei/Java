package com.mdsd.cloud.controller.other;

import com.mdsd.cloud.controller.other.dto.AttachmentDto;
import com.mdsd.cloud.controller.other.service.AttService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 附件
 *
 * @author huxiaoneng
 */
@RestController
@RequestMapping("/attController/v1")
@RequiredArgsConstructor
@Api(value = "WEB - 附件信息", produces = MediaType.APPLICATION_JSON_VALUE, tags = "att")
public class AttController {

    private final AttService attService;
    /**
     * 附件信息详情
     */
    @ApiOperation(value = "Other - 附件信息")
    @GetMapping(name = "详情", value = "/getAtt/{missionId}")
    public ResponseDto<List<AttachmentDto>> getAtt(@PathVariable String missionId) {

        return ResponseUtil.wrapSuccess(this.attService.getAtt(missionId));
    }

}
