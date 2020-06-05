package com.framework.mvc.annotation;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/4 14:57
 */

import com.framework.mvc.type.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识Controller的方法与请求路径和请求方法的映射关系
 */
//用在类上和方法上
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    //请求路径
    String value()default "";
    //请求方法
    RequestMethod method() default RequestMethod.GET;
}