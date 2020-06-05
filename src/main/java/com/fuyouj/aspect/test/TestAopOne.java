package com.fuyouj.aspect.test;

import com.framework.aop.annotation.Aspect;
import com.framework.aop.annotation.Order;
import com.framework.aop.aspect.DefaultAspect;
import com.framework.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/2 0:40
 */
@Aspect(pointCut = "execution(* com.fuyouj.controller..*.*(..))")
@Order(0)
@Slf4j
public class TestAopOne implements DefaultAspect {
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
       log.info("我是切面逻辑before order0，我增强的目标类是{}，我增强的目标方法是{}，方法参数{}"
        ,targetClass.getSimpleName(),method.getName(),args);
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        Integer res = (Integer) returnValue;
        log.info("我是切面逻辑after order0，我增强的目标类是{}，我增强的目标方法是{}，方法参数{}"
                ,targetClass.getSimpleName(),method.getName(),args);
        res++;
        return res;
    }


}