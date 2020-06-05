package com.demo.pattern.factory.entity;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:42
 **/
public class DellKeyBoard implements KeyBoard {
    @Override
    public void sayHello() {
        System.out.println("我是戴尔键盘");
    }
}
