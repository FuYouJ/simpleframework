package com.framework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Desc 封装待处理的Controller及其方法实例和参数的映射
 * @Author FuYouJ
 * @date 2020/6/4 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {
    //controller对应的class
    private Class<?> controllerClass;
    //执行的controller方法实例
    private Method invokeMethod;
    //用来保存方法参数名称及其对应的参数类型
    private Map<String,Class<?>> methodParameters;
}