package com.mdsd.cloud.controller.auth;

import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenOup;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2024-07-12]
 */
@Tag(name = "鉴权")
@RequestMapping(name = "鉴权", path = "/auth")
@RestController
public class AuthController {

    private AuthService service;

    public AuthController(AuthService service) {

        this.service = service;
    }

    @PostMapping(name = "换取AccessToken(鉴权)", path = "/getToken")
    public ResponseDto<GetTokenOup> getToken(@RequestBody GetTokenInp param) {

        return ResponseDto.wrapSuccess(service.getToken(param));
    }

}
