package com.mdsd.cloud.config;

import com.mdsd.cloud.feign.AuthServerFeign;
import com.mdsd.cloud.response.exception.BaseResponseEnum;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author WangYunwei [2022-08-30]
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    AuthServerFeign authServerFeign;

    @Override
    public boolean preHandle(final HttpServletRequest request, @NotNull final HttpServletResponse response, @NotNull final Object handler) {

        final String requestURI = request.getRequestURI();
        log.info("权限拦截器 => {} {}", request.getMethod(), request.getRequestURL());
        //不拦截URI
        final ArrayList<String> ignorePathList = new ArrayList<String>() {{
            this.add("/error");
            this.add("/login");
            this.add("/connectSSE");
            this.add("/pushDateToSSE");
            this.add("/triagePatientPageList");
            this.add("/triagePatientDetail");
        }};
        final long count = ignorePathList.stream().filter(requestURI::contains).count();
        if (0 != count || "OPTIONS".equals(request.getMethod())) {
            return true;
        }
        //校验业务Token
        final String businessJwtToken = request.getHeader("jwt-token");
        if (Strings.isBlank(businessJwtToken)) {
            throw new BusinessException(BaseResponseEnum.AUTHORITY_EXCEPTION);
        }
        log.info("Auth::checkTokenExpire - [InParameter]=> {}", businessJwtToken);
        final Map<String, Object> resultMap = this.authServerFeign.checkTokenExpire(businessJwtToken);
        return resultMap.get("status").equals(200);
    }
}
