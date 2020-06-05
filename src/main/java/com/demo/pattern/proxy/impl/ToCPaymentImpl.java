package com.demo.pattern.proxy.impl;

import com.demo.pattern.proxy.ToCPayment;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 13:52
 */
public class ToCPaymentImpl implements ToCPayment {
    @Override
    public void pay() {
        System.out.println("以用户的名义进行支付");
    }
}