package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.IBMMouse;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:19
 **/
public class IBMMouserFactory extends LenovoFactory {
    @Override
    public Mouse createMouse() {
        super.createMouse();
        return new IBMMouse();
    }
}
