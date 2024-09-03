package com.mdsd.cloud.controller.tyjw;

import com.mdsd.cloud.controller.tyjw.dto.GetTokenInp;
import com.mdsd.cloud.controller.tyjw.dto.GetTokenOup;
import com.mdsd.cloud.controller.tyjw.service.AuthService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2024-07-12]
 */
@Tag(name = "天宇 - 鉴权API")
@RequestMapping(name = "鉴权API", path = "/auth")
@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {

        this.service = service;
    }

    @Operation(summary = "GetToken",description = "换取AccessToken(鉴权),同时执行TCP连接注册")
    @PostMapping(name = "换取AccessToken(鉴权),同时执行TCP连接注册", path = "/getToken")
    public ResponseDto<GetTokenOup> getToken(@RequestBody GetTokenInp param) {

        return ResponseDto.wrapSuccess(service.getToken(param));
    }

}
