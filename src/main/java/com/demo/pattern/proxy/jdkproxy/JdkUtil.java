package com.demo.pattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 15:19
 */
public class JdkUtil {
    public static <T> T newProxyInstance(T targetObject, InvocationHandler handler){
        ClassLoader classLoader = targetObject.getClass().getClassLoader();
        Class<?>[] interfaces = targetObject.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader,interfaces,handler);
    }
}