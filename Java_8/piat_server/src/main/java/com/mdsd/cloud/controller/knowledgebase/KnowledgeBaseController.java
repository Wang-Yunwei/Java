package com.mdsd.cloud.controller.knowledgebase;

import com.mdsd.cloud.controller.knowledgebase.entity.KnowledgeCategoryEntity;
import com.mdsd.cloud.controller.knowledgebase.entity.PatientSayEntity;
import com.mdsd.cloud.controller.knowledgebase.service.KnowledgeBaseService;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wgy
 */
@RestController
@RequestMapping("/knowledgebase")
@Api(value = "WEB - KnowledgeBaseController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "知识库配置")
public class KnowledgeBaseController {

    KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(final KnowledgeBaseService knowledgeBaseService) {

        this.knowledgeBaseService = knowledgeBaseService;
    }

    @ApiOperation(value = "知识库 查询(0:儿科小儿科 1 :急诊大类 2：急诊主诉 3：主诉依据 4：依据等级 )")
    @PostMapping("/getKnowledgeBase")
    public ResponseDto<?> getMedicineType(final String type, final String medicinetype) {

        return ResponseUtil.wrapSuccess(this.knowledgeBaseService.getMedicineType(type, medicinetype));
    }

    @ApiOperation(value = "常用主诉查询（type:1 medicinetype=类型，type:2 medicinetype=大类 categoryid ）")
    @PostMapping("/getOftenKnowledgeBase")
    public ResponseDto<?> getOftenKnowledgeBase(final String type, final String medicinetype) {

        return ResponseUtil.wrapSuccess(this.knowledgeBaseService.getOftenKnowledgeBase(type, medicinetype));
    }

    @ApiOperation(value = "根据内伤外伤查询全部数据")
    @PostMapping("/getListKnowledgeBase")
    public ResponseDto<?> getListKnowledgeBase(final String medicinetype) {

        final List<KnowledgeCategoryEntity> b = this.knowledgeBaseService.getListKnowledgeBase(medicinetype);
        return ResponseUtil.wrapSuccess(b);
    }

    @ApiOperation(value = "知识库搜索")
    @PostMapping("/searchknowledgebase")
    public ResponseDto<?> searchknowledgebase(final String medicinetype, final String knowledgeName) {

        final List<PatientSayEntity> b = this.knowledgeBaseService.searchknowledgebase(medicinetype, knowledgeName);
        return ResponseUtil.wrapSuccess(b);
    }

    @ApiOperation(value = "卒中调用-获取依据")
    @PostMapping("/getPatientSay")
    public ResponseDto<?> getPatientSay(final String PatientSayid) {

        return ResponseUtil.wrapSuccess(this.knowledgeBaseService.getPatientSay(PatientSayid));
    }
}
