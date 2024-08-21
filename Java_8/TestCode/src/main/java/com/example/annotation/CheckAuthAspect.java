///*------------------------------------------------------------------------------
// *******************************************************************************
// * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2020
// *
// * The copyright to the computer program(s) herein is the property of
// * CMSS Co.,Ltd. The programs may be used and/or copied only with written
// * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
// * stipulated in the agreement/contract under which the program(s) have been
// * supplied.
// *******************************************************************************
// *----------------------------------------------------------------------------*/
//package com.xue.annotation.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.xue.annotation.CheckAuth;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.Objects;
//
///**
// * WangYunwei [2020-09-02 17:17:07]
// *
// * @Aspect 作用是把当前类标识为一个切面供容器读取
// */
//@Aspect
//@Component
//public class CheckAuthAspect {
//
//    // 切点
//    @Pointcut("@annotation(com.example.demo.annotation.CheckAuth)")
//    public void checkAspect() {
//
//    }
//
//    /**
//     * 前置通知
//     *
//     * @Before 标识一个前置增强方法, 相当于BeforeAdvice的功能, 进入方法前执行
//     */
//    @Before("checkAspect()")
//    public void doBefore(final JoinPoint joinPoint) {
//
//        // 1.获取注解信息
//        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        final Method method = signature.getMethod();
//        if (Objects.isNull(method)) {
//            return;
//        }
//        final CheckAuth annotation = method.getAnnotation(CheckAuth.class);
//        annotation.description(); // 获取到注解中字段描述
//
//        //2. 获取HttpServletRequest
//        final HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest();
//        //2.1 获取HttpServletRequest中的参数 (注: 以下获取参数方式只能获取 Query Params 中的入参 )
//        req.getParameter(null);// 通过指定名称获取参数值
//        req.getParameterNames();// 获取所有有参数的名字
//        req.getParameterValues(null);// 通过指定名称获取参数值数组,有可能一个名字对应多个值,例如表单中的多个复选框使用相同的name时
//        req.getParameterMap();// 获取所有参数对应的Map
//        //2.2 获取HttpServletRequest中的参数 (注: 获取 Request Payload 中入参)
//        try {
//            final Object o = JSON.parseObject(req.getInputStream(), JSONObject.class);
//        } catch (final IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
