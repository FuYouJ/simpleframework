package com.framework.aop.annotation;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 21:22
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义切面顺序
 */
//作用在类上
@Target(ElementType.TYPE)
//运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    //越小优先级别越高
    int value();
}
