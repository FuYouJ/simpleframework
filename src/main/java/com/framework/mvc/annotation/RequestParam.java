package com.framework.mvc.annotation;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/4 15:01
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求的方法参数名称
 */
//作用与参数上
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    //方法参数名称
    String value() default "";
    //默认需要这个参数
    boolean required() default true;
}
