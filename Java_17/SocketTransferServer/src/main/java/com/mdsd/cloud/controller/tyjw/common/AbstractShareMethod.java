package com.mdsd.cloud.controller.tyjw.common;

import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.dto.GetTokenInp;
import com.mdsd.cloud.controller.tyjw.service.AuthService;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

/**
 * @author WangYunwei [2024-08-21]
 */
public abstract class AbstractShareMethod {

    @Autowired
    private AuthService authService;

    public final AuthSingleton auth = AuthSingleton.getInstance();

    public AbstractShareMethod() {
    }

    protected <T> T handleAuth(Supplier<T> supplier) {
        if (auth.getCompanyId() == null || !StringUtils.isNoneBlank(auth.getAccessToken())) {
            authService.getToken(new GetTokenInp());
        }
        return supplier.get();
    }

    public <T> T processResult(ResponseTy<T> result) {

        if (0 == result.getState()) {
            return result.getContent();
        } else {
            throw new BusinessException(String.valueOf(result.getState()), result.getMessage());
        }
    }
}
