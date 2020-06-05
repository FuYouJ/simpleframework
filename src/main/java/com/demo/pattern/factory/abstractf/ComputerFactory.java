package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.KeyBoard;
import com.demo.pattern.factory.entity.Mouse;

public interface ComputerFactory {
    Mouse createMouse();
    KeyBoard createKeyBoard();
}
