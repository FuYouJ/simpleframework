package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.DellKeyBoard;
import com.demo.pattern.factory.entity.DellMouse;
import com.demo.pattern.factory.entity.KeyBoard;
import com.demo.pattern.factory.entity.Mouse;

/**
 * @author FuYouJ
 * @date 2020/5/5  23:44
 **/
public class DellComputerFactory implements ComputerFactory {
    @Override
    public Mouse createMouse() {
        return new DellMouse();
    }

    @Override
    public KeyBoard createKeyBoard() {
        return new DellKeyBoard();
    }
}
