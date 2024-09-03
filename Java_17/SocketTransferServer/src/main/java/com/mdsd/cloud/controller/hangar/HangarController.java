package com.mdsd.cloud.controller.hangar;

import com.mdsd.cloud.controller.hangar.service.IHangarService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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


    @Operation(summary = "GetToken",description = "换取AccessToken(鉴权),同时执行TCP连接注册")
    @PostMapping(name = "换取AccessToken(鉴权),同时执行TCP连接注册", path = "/getToken")
    public ResponseDto<Map<String,String>> operate(){

        return ResponseDto.wrapSuccess();
    }
}
