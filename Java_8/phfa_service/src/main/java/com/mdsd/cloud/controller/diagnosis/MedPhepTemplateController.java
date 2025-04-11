package com.mdsd.cloud.controller.diagnosis;

import com.mdsd.cloud.controller.diagnosis.service.MedPhepTemplateService;
import com.mdsd.cloud.entity.MedPhepTemplateEntity;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板
 *
 * @author wcq
 */
@RestController

@RequestMapping(value = "/medPhepTemplate")
@Api(value = "MedPhepTemplateController", tags = "模板")
public class MedPhepTemplateController {

    MedPhepTemplateService medPhepTemplateService;

    public MedPhepTemplateController(final MedPhepTemplateService medPhepTemplateService) {

        this.medPhepTemplateService = medPhepTemplateService;
    }

    @PostMapping(value = "save")
    public void save(@RequestBody final MedPhepTemplateEntity medPhepTemplateEntity) {

        this.medPhepTemplateService.save(medPhepTemplateEntity);
    }

    @PostMapping(value = "update")
    public void update(@RequestBody final MedPhepTemplateEntity medPhepTemplateEntity) {

        this.medPhepTemplateService.update(medPhepTemplateEntity);
    }

    @GetMapping(value = "delete")
    public void deleteById(@RequestParam("id") final String id) {

        this.medPhepTemplateService.deleteById(id);
    }

    /**
     * 查询模板列表
     */
    @GetMapping(value = "queryList/{tempType}")
    public ResponseDto<List<MedPhepTemplateEntity>> queryByLicensePlate(@PathVariable("tempType") final String tempType) {

        return ResponseUtil.wrapSuccess(this.medPhepTemplateService.queryList(tempType));
    }
}
