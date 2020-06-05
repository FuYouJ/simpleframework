package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.KeyBoard;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:45
 **/
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        HpComputerFactory factory = new HpComputerFactory();
        Mouse mouse = factory.createMouse();
        KeyBoard keyBoard = factory.createKeyBoard();
        mouse.sayHi();
        keyBoard.sayHello();
    }
}
