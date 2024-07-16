package com.mdsd.cloud.controller.auth.service;

import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenOup;
import com.mdsd.cloud.response.ResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author WangYunwei [2024-07-12]
 */
public interface AuthService {

    /**
     * 换取AccessToken(鉴权)
     */
    GetTokenOup getToken(GetTokenInp param);
}
