package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.HpKeyBoard;
import com.demo.pattern.factory.entity.HpMouse;
import com.demo.pattern.factory.entity.KeyBoard;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:45
 **/
public class HpComputerFactory implements ComputerFactory {
    @Override
    public Mouse createMouse() {
        return new HpMouse();
    }

    @Override
    public KeyBoard createKeyBoard() {
        return new HpKeyBoard();
    }
}
