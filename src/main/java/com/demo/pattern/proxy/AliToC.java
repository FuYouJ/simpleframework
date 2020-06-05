package com.demo.pattern.proxy;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 13:53
 */
public class AliToC implements ToCPayment {
    private ToCPayment toCPayment;
    public AliToC(ToCPayment toCPayment){
        this.toCPayment = toCPayment;
    }
    @Override
    public void pay() {
        beforePay();
        toCPayment.pay();
        afterPay();
    }

    private void afterPay() {
        System.out.println("支付给收钱方");
    }

    private void beforePay() {
        System.out.println("从银行卡取款");
    }
}