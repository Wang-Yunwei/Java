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

import java.util.Map;

/**
 * @author WangYunwei [2024-09-03]
 */
@Tag(name = "机库 - API")
@RequestMapping(name = "机库API", path = "/hangar")
@RestController
public class HangarController {

    final IHangarService hangarService;

    public HangarController(IHangarService hangarService) {
        this.hangarService = hangarService;
    }

    @Operation(summary = "指令操作",description = "指令: 0x00-舱门, 0x01-推杆, 0x02-空调, 0x03-无人机; 动作: 0x01-开启, 0x02-关闭 (具体参数请查看入参 Schema)")
    @PostMapping(name = "指令操作", path = "/operate")
    public ResponseDto<Map<String,String>> operate(@RequestBody OperateInp param){

        return ResponseDto.wrapSuccess(hangarService.operate(param));
    }
}
