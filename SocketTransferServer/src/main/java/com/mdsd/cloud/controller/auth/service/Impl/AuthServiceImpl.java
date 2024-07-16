package com.mdsd.cloud.controller.auth.service.Impl;

import com.mdsd.cloud.config.TyRequestInterceptor;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenOup;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author WangYunwei [2024-07-12]
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final SocketClient socketClient;

    private final EApiFeign feign;

    /**
     * 换取AccessToken(鉴权)
     */
    @Override
    public GetTokenOup getToken(GetTokenInp param) {

        GetTokenOup result = new GetTokenOup();
        ResponseTy<GetTokenOup> token = feign.getToken(param);
        if (0 == token.getState()) {
            Integer companyId = token.getContent().getCompanyId();
            String accessToken = token.getContent().getAccessToken();
            // 注册成功后,执行设置 Http 统一请求头
            var tri = new TyRequestInterceptor();
            tri.setCompanyId(String.valueOf(companyId));
            tri.setAccessToken(accessToken);
            // 执行 TCP 连接注册
            socketClient.getRegisterInp().setUserNum(companyId).setAccessToken(accessToken.getBytes()).setDataLength(accessToken.length() + 5);
            socketClient.connect();
            // 返回前端数据
            result.setCompanyId(companyId);
            result.setAccessToken(accessToken);
        }
        return result;
    }
}
