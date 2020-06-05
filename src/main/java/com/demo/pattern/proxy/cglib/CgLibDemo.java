package com.demo.pattern.proxy.cglib;


import com.demo.pattern.proxy.ToCPayment;
import com.demo.pattern.proxy.impl.ToCPaymentImpl;
import com.demo.pattern.proxy.jdkproxy.AliPayInvocationHandler;
import com.demo.pattern.proxy.jdkproxy.JdkUtil;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 20:23
 */
public class CgLibDemo {
    public static void main(String[] args) {
       /* CommonPay commonPay = new CommonPay();
        AliPayMethodInterceptor interceptor = new AliPayMethodInterceptor();
        CommonPay commonPayProxy = CgLibUtil.createProxy(commonPay,interceptor);
        commonPayProxy.pay();*/
        //tocPayMent的实现类
       /* ToCPayment toCPayment = new ToCPaymentImpl();
        AliPayMethodInterceptor interceptor = new AliPayMethodInterceptor();
        ToCPayment proxy = CgLibUtil.createProxy(toCPayment, interceptor);
        proxy.pay();*/
        //尝试使用JDK动态代理
        CommonPay commonPay = new CommonPay();
        AliPayInvocationHandler handler = new AliPayInvocationHandler(commonPay);
        CommonPay newProxyInstance = JdkUtil.newProxyInstance(commonPay, handler);
        newProxyInstance.pay();
    }
}