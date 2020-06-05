package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.DellMouse;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:10
 **/
public class DellMousseFactory implements MouseFactory {
    @Override
    public Mouse createMouse() {
        return new DellMouse();
    }
}
