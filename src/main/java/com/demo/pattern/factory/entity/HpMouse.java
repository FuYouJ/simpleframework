package com.demo.pattern.factory.entity;

/**
 * @author FuYouJ
 * @date 2020/5/5  20:49
 **/
public class HpMouse implements Mouse {
    @Override
    public void sayHi() {
        System.out.println("我是惠普的鼠标");
    }
}
