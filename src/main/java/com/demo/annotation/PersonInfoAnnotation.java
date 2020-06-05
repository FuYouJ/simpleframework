package com.demo.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author FuYouJ
 * @date 2020/5/7  15:28
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonInfoAnnotation {
    String name();
    int age()default 19;
    String gender()default "男";
    //开发语言
    String[] language();
}
