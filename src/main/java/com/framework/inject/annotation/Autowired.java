package com.framework.inject.annotation;

import java.lang.annotation.*;

/**
 * 支持成员变量级别
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String value() default "";
}
