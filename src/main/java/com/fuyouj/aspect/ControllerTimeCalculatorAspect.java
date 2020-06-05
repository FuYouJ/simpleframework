package com.fuyouj.aspect;

import com.framework.aop.annotation.Aspect;
import com.framework.aop.annotation.Order;
import com.framework.aop.aspect.DefaultAspect;
import com.framework.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/1 14:09
 */

/**
 * 切面类
 */
@Slf4j
//@Aspect(Controller.class)
//@Aspect(pointCut = "execution(* com.fuyouj.controller.frontend..*.*(..))")
@Order(0)
public class ControllerTimeCalculatorAspect implements DefaultAspect {
     private long timeStampCache;
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("controller开始计时，执行的类是[{}],执行的方法是[{}],参数是[{}]",
                targetClass.getName(),method.getName(),args);
        timeStampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        timeStampCache = System.currentTimeMillis() - timeStampCache;
        log.info("controller结束计时，执行的类是[{}],执行的方法是[{}],参数是[{}],执行的时间是[{}] ms",
                targetClass.getName(),method.getName(),args,timeStampCache);
        return returnValue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {

    }
}