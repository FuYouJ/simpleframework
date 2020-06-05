package com.framework.aop.aspect;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 21:24
 */

import java.lang.reflect.Method;

/**
 * 定义切面的通用骨架
 * 通过钩子方法实现
 */
public interface  DefaultAspect {
    /**
     * @param targetClass 被代理的目标类型
     * @param method 被代理的方法
     * @param args 方法的参数
     * @throws Throwable
     */
    default void before(Class<?> targetClass, Method method,Object[] args) throws Throwable{

    } ;

    /**
     *
     * @param targetClass 被代理的目标类型
     * @param method 被代理的方法
     * @param args 方法的参数
     * @param returnValue 返回值
     * @return
     * @throws Throwable
     */
   default  Object afterReturning(Class<?> targetClass,Method method
            ,Object[] args,Object returnValue) throws Throwable{
       return  returnValue;
   }

    /**
     *
     * @param targetClass 被代理的目标类型
     * @param method 被代理的方法
     * @param args 方法的参数
     * @param e 异常
     * @throws Throwable
     */
    default void afterThrowing(Class<?> targetClass,
                              Method method,Object[] args,Throwable e) throws Throwable{

    }
}