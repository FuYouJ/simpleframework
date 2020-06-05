package com.demo.pattern.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 20:39
 */
public class AliPayMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        beforePay();
        //执行被代理方法
        Object invoke = methodProxy.invokeSuper(o, objects);
        afterPay();
        return invoke;
    }
    private void afterPay() {
        System.out.println("支付给收钱方");
    }

    private void beforePay() {
        System.out.println("从银行卡取款");
    }
}