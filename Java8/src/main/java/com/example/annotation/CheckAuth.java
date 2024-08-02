/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2020
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.xue.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在,在运行时可以通过反射获取到
@Target({ElementType.FIELD, ElementType.METHOD}) // 定义注解的作用目标**作用范围: 字段、枚举的常量/方法
@Documented // 说明该注解将被包含在Javadoc中
@Component
public @interface CheckAuth {

    /**
     * 字段描述
     */
    String description();
}
