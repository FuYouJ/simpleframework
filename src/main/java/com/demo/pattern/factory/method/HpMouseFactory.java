package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.HpMouse;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:09
 **/
public class HpMouseFactory implements MouseFactory {
    @Override
    public Mouse createMouse() {
        return new HpMouse();
    }
}
