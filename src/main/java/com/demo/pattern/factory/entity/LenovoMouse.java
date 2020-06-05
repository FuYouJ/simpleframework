package com.demo.pattern.factory.entity;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:16
 **/
public class LenovoMouse implements Mouse {
    @Override
    public void sayHi() {
        System.out.println("我是联想的鼠标");
    }
}
