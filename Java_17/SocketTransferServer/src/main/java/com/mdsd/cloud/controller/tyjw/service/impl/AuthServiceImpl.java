package com.mdsd.cloud.controller.tyjw.service.impl;

import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.dto.GetTokenInp;
import com.mdsd.cloud.controller.tyjw.dto.GetTokenOup;
import com.mdsd.cloud.controller.tyjw.service.AuthService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.utils.MD5HashGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author WangYunwei [2024-07-12]
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final EApiFeign feign;

    public AuthServiceImpl(EApiFeign feign) {
        this.feign = feign;
    }

    /**
     * 获取 AccessToken(天宇)
     */
    @Override
    public void getToken() {

        getToken(new GetTokenInp());
    }

    /**
     * 获取 AccessToken(天宇)
     */
    @Override
    public GetTokenOup getToken(GetTokenInp param) {

        log.info("获取 AccessToken");
        String str = param.getAccessKeyId() + param.getAccessKeySecret() + param.getTimeStamp();
        param.setEncryptStr(MD5HashGenerator.generateMD5(str));
        ResponseTy<GetTokenOup> result = feign.getToken(param);
        if(0== result.getState()){
            AuthSingleton.getInstance().setCompanyId(result.getContent().getCompanyId());
            AuthSingleton.getInstance().setAccessToken(result.getContent().getAccessToken());
            return result.getContent();
        }else{
            throw new BusinessException(String.valueOf(result.getState()), result.getMessage());
        }
    }
}

