package com.demo.pattern.proxy;

import com.demo.pattern.proxy.impl.ToCPaymentImpl;
import com.demo.pattern.proxy.jdkproxy.AliPayInvocationHandler;
import com.demo.pattern.proxy.jdkproxy.JdkUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 13:56
 */
public class ProxyDemo {
    public static void main(String[] args) {
/*
        ToCPayment toCProxy = new AliToC( new ToCPaymentImpl());
        toCProxy.pay();*/
        //被代理的对象
        ToCPayment toCPayment = new ToCPaymentImpl();
        AliPayInvocationHandler handler = new AliPayInvocationHandler(toCPayment);
        ToCPayment payment = JdkUtil.newProxyInstance(toCPayment, handler);
        payment.pay();
    }
}