package com.demo.pattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 15:10
 */
public class AliPayInvocationHandler implements InvocationHandler {
    //用来保存被代理的对象
    private Object targetObject;
    public AliPayInvocationHandler(Object targetObject){
        this.targetObject = targetObject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforePay();
        //目标类 方法参
        Object result = method.invoke(targetObject, args);
        afterPay();
        return result;
    }
    private void afterPay() {
        System.out.println("支付给收钱方");
    }

    private void beforePay() {
        System.out.println("从银行卡取款");
    }
}