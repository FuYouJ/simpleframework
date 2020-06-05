package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.LenovoMouse;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:16
 **/
public class LenovoFactory implements MouseFactory {
    @Override
    public Mouse createMouse() {
        return new LenovoMouse();
    }
}
