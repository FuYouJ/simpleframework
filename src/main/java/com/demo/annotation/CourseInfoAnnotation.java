package com.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface CourseInfoAnnotation {
    //课程名称
    String courseName();
    //标签
    String courseTag();
    //简介
   String courseProfile();
   //课程序号
   int courseIndex() default 303;
}
