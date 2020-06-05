package com.demo.pattern.factory.entity;

/**
 * @author FuYouJ
 * @date 2020/5/5  20:48
 **/
public class DellMouse implements Mouse{
    @Override
    public void sayHi() {
        System.out.println("我是戴尔的鼠标");
    }
}
