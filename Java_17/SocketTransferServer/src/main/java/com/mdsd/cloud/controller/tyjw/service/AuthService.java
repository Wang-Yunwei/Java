package com.mdsd.cloud.controller.tyjw.service;

import com.mdsd.cloud.controller.tyjw.dto.GetTokenInp;
import com.mdsd.cloud.controller.tyjw.dto.GetTokenOup;

/**
 * @author WangYunwei [2024-07-12]
 */
public interface AuthService {

    /**
     * 获取 AccessToken(天宇)
     */
    void getToken();

    /**
     * 获取 AccessToken(天宇)
     */
    GetTokenOup getToken(GetTokenInp param);
}
