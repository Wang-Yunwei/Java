package com.mdsd.cloud.config;

import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.feign.AuthServerFeign;
import com.mdsd.cloud.response.exception.BaseResponseEnum;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2022-08-30]
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    AuthServerFeign authServerFeign;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 在请求处理之前执行的方法
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, @NotNull final HttpServletResponse response, @NotNull final Object handler) {
        MDC.put("requestIP",request.getRemoteAddr());
        MDC.put("userAgent",request.getHeader("User-Agent"));
        MDC.put("requestDispatcherPath",request.getServletPath());
        //不拦截URI
        final ArrayList<String> ignorePathList = new ArrayList<String>() {{
            this.add("/error");
            this.add("/login");
            this.add("/connectSSE");
        }};
        final String requestURI = request.getRequestURI();
        final long count = ignorePathList.stream().filter(requestURI::contains).count();
        if (0 != count || "OPTIONS".equals(request.getMethod())){return true;}
        // 公司项目请求验证
        final String mdsd = request.getHeader("mdsd");
        if (Strings.isNotBlank(mdsd)) {
            final String das = DigestUtils.md5Hex("das");
            if (mdsd.equals(das)) {
                return true;
            } else {
                throw new BusinessException(BaseResponseEnum.AUTHORITY_EXCEPTION);
            }
        }
        // 拦截请求,校验业务Token
        final String jwtToken = request.getHeader("Jwt-Token");
        if (Strings.isBlank(jwtToken)) {
            throw new BusinessException(BaseResponseEnum.AUTHORITY_EXCEPTION);
        }
        // 通过token查询redis中是否存在用户信息
        String jwtTokenKey = String.format("loginUser:%s",DigestUtils.md5Hex(jwtToken));
        Long expire = redisTemplate.getExpire(jwtTokenKey, TimeUnit.MINUTES);
        // 如果redis中不存在则调用auth验证
        if(expire > 0){
            // 登录信息未过期,如果过期时间小于15分钟则重置过期时间
            if(expire < 15){
                redisTemplate.expire(jwtTokenKey, CommonConstants.REDIS_TIME_OUT,TimeUnit.MINUTES);
            }
            Object username = redisTemplate.opsForValue().get(jwtTokenKey);
            MDC.put("username", username.toString());
        }else{
            // 登录信息过期
            throw new BusinessException(BaseResponseEnum.AUTHORITY_TOKEN);
        }
        log.info("权限拦截器 => {} {}", request.getMethod(), request.getRequestURL());
        return true;
    }
}
