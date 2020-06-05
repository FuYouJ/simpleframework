package com.demo.pattern.factory.entity;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:18
 **/
public class IBMMouse implements Mouse {
    @Override
    public void sayHi() {
        System.out.println("我是联想的IBM鼠标");
    }
}
