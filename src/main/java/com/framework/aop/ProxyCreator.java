package com.framework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/1 0:15
 */
public class ProxyCreator {
    /**
     *创建代理 对象并返回
     * @param targetClass
     * @param interceptor
     * @return
     */
    public static Object createProxy(Class<?> targetClass, MethodInterceptor interceptor){
        Object o = Enhancer.create(targetClass, interceptor);
        return o;
    }
}