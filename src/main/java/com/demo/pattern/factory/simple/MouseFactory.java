package com.demo.pattern.factory.simple;

import com.demo.pattern.factory.entity.DellMouse;
import com.demo.pattern.factory.entity.HpMouse;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  20:50
 **/
public class MouseFactory {
    public static void main(String[] args) {
        Mouse mouse = MouseFactory.createMouse(1);
        mouse.sayHi();
    }
    public static Mouse createMouse(int type){
        switch (type){
            case 0 : return new DellMouse();
            case 1 : return new HpMouse();
            default : return new DellMouse();
        }
    }
}
