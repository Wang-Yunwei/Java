package com.mdsd.cloud.controller.auth;

import com.mdsd.cloud.controller.auth.service.IAuthService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2024-09-09]
 */
@Tag(name = "鉴权 - API")
@RequestMapping("/auth")
@RestController
public class AuthController {

    IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "获取用户信息")
    @GetMapping(name = "鉴权 - 获取用户信息")
    public ResponseDto<String> getUserInfo() {

        return ResponseDto.wrapSuccess();
    }
}
