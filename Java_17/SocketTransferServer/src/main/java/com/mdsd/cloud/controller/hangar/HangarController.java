package com.mdsd.cloud.controller.hangar;

import com.mdsd.cloud.controller.hangar.dto.OperateInp;
import com.mdsd.cloud.controller.hangar.service.IHangarService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2024-09-03]
 */
@Tag(name = "机库API")
@RequestMapping(name = "机库API", path = "/hangar")
@RestController
public class HangarController {

    final IHangarService hangarService;

    public HangarController(IHangarService hangarService) {
        this.hangarService = hangarService;
    }

    @Operation(summary = "operate",description = "操作机库")
    @PostMapping(name = "操作机库", path = "/operate")
    public ResponseDto<String> operate(@RequestBody OperateInp param){

        hangarService.operate(param);
        return ResponseDto.wrapSuccess();
    }
}
