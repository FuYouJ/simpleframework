package com.demo.pattern.proxy.cglib;

import com.demo.pattern.factory.entity.IBMMouse;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 20:43
 */
public class CgLibUtil {
    public static  <T> T  createProxy(T targetObject , MethodInterceptor interceptor){
       return (T) Enhancer.create(targetObject.getClass(), interceptor);
    }
}