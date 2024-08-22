package com.mdsd.cloud.controller.tyjw.common;

import com.mdsd.cloud.controller.tyjw.dto.AuthSingleton;
import com.mdsd.cloud.controller.tyjw.dto.GetTokenInp;
import com.mdsd.cloud.controller.tyjw.service.AuthService;
import com.mdsd.cloud.controller.tyjw.service.impl.AuthServiceImpl;
import com.mdsd.cloud.enums.StateEnum;
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

    public AbstractShareMethod() {}

    protected <T> T handleAuth(Supplier<T> supplier) {
        if (auth.getCompanyId() == null || !StringUtils.isNoneBlank(auth.getAccessToken())) {
            authService.getToken(new GetTokenInp());
        }
        return supplier.get();
    }

    public <T> T processResult(ResponseTy<T> result) {
        StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
        if (stateEnum != null) {
            return switch (stateEnum) {
                case STATE_0 -> result.getContent();
                case STATE_201 -> {
                    authService.getToken(new GetTokenInp());
                    throw new BusinessException("Unexpected state: STATE_201 - Token refresh should be handled outside of this method.");
                }
                default -> throw new BusinessException(stateEnum.getDescription());
            };
        } else {
            throw new BusinessException(result.toString());
        }
    }
}
