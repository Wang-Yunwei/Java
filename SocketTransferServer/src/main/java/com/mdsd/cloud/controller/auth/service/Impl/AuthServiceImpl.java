package com.mdsd.cloud.controller.auth.service.Impl;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenOup;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.controller.transfer.dto.BaseInp;
import com.mdsd.cloud.socket.SocketClient;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author WangYunwei [2024-07-12]
 */
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
        ResponseTy<GetTokenOup> token = feign.getToken(param);
        if (0 == token.getState()) {
            Integer companyId = token.getContent().getCompanyId();
            String accessToken = token.getContent().getAccessToken();
            AuthSingleton.getInstance().setCompanyId(companyId);
            AuthSingleton.getInstance().setAccessToken(accessToken);
            // 执行TCP注册连接
            socketClient.connect();
            // 返回前端数据
            result.setCompanyId(companyId);
            result.setAccessToken(accessToken);
        }
        return result;
    }
}

