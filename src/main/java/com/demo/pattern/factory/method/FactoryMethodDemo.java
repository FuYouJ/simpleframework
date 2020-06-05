package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:11
 **/
public class FactoryMethodDemo {
    public static void main(String[] args) {
        LenovoFactory factory = new IBMMouserFactory();
        Mouse mouse = factory.createMouse();
        mouse.sayHi();
    }
}
