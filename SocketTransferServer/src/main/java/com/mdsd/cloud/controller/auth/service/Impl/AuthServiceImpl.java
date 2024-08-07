package com.mdsd.cloud.controller.auth.service.Impl;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenOup;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.component.SocketClient;
import com.mdsd.cloud.utils.MD5HashGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author WangYunwei [2024-07-12]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final EApiFeign feign;

    private final SocketClient socketClient;

    /**
     * 换取AccessToken(鉴权)
     */
    @Override
    public GetTokenOup getToken(GetTokenInp param) {

        GetTokenOup result = new GetTokenOup();
        String s = param.getAccessKeyId() + param.getAccessKeySecret() + param.getTimeStamp();
        param.setEncryptStr(MD5HashGenerator.generateMD5(s));
        ResponseTy<GetTokenOup> token = feign.getToken(param);
        if (0 == token.getState()) {
            log.info("项目启动后直接调用 getToken!");
            Integer companyId = token.getContent().getCompanyId();
            String accessToken = token.getContent().getAccessToken();
            AuthSingleton.getInstance().setCompanyId(companyId);
            AuthSingleton.getInstance().setAccessToken(accessToken);

            // 注册 socket 连接
            socketClient.connect();

            // 返回前端数据
            result.setCompanyId(companyId);
            result.setAccessToken(accessToken);
        }
        return result;
    }
}

