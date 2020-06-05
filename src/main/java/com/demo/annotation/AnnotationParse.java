package com.demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author FuYouJ
 * @date 2020/5/7  16:06
 **/
public class AnnotationParse {
    public static void main(String[] args) throws ClassNotFoundException {

       // parseTypeAnnotation();
      //  parseFiledAnnotation();
        parseMethod();
    }
    //解析类的注解
   public  static void parseTypeAnnotation() throws ClassNotFoundException {
       Class clazz = Class.forName("com.demo.annotation.Course");
       //这里只是获取对象的注解，而不是方法和成员变量的注解
       Annotation[] annotations = clazz.getAnnotations();
       for (Annotation annotation : annotations) {
           CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
           System.out.println("名字: "+courseInfoAnnotation.courseName());
           System.out.println("标签: "+courseInfoAnnotation.courseTag());
           System.out.println("描述: "+courseInfoAnnotation.courseProfile());
           System.out.println("序号: "+courseInfoAnnotation.courseIndex());
       }
   }
    //解析成员注解
   public static void parseFiledAnnotation() throws ClassNotFoundException {
       Class clazz = Class.forName("com.demo.annotation.Course");
       Field[] fields = clazz.getDeclaredFields();
       for (Field field : fields) {
           //判断是指定注解
           boolean b = field.isAnnotationPresent(PersonInfoAnnotation.class);
           if (b){
               PersonInfoAnnotation personInfoAnnotation = field.getAnnotation(PersonInfoAnnotation.class);
               System.out.println(personInfoAnnotation.name());
               for (String s : personInfoAnnotation.language()) {
                   System.out.println(s);
               }
           }
       }
   }
   //解析方法注解
    public static void parseMethod() throws ClassNotFoundException {
        Class clazz = Class.forName("com.demo.annotation.Course");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean b = method.isAnnotationPresent(CourseInfoAnnotation.class);
            if (b){
                CourseInfoAnnotation annotation = method.getAnnotation(CourseInfoAnnotation.class);
                System.out.println(annotation.courseName());
            }
        }
    }

}
