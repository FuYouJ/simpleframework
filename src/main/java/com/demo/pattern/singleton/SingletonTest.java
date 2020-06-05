package com.demo.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author FuYouJ
 * @date 2020/5/10  15:40
 **/
public class SingletonTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取实例
        System.out.println(EnumStarvingSingleton.getInstance());
        //通过反射获取
        Class<EnumStarvingSingleton> clazz = EnumStarvingSingleton.class;
        //获取任意公开的无参构造方法
        Constructor<EnumStarvingSingleton> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        EnumStarvingSingleton enumStarvingSingleton = constructor.newInstance();
        /**
         * com.demo.pattern.singleton.EnumStarvingSingleton@7f31245a
         * com.demo.pattern.singleton.EnumStarvingSingleton@7f31245a
         * 却没有成功创造 第二个枚举类
         */
        System.out.println(enumStarvingSingleton.getInstance());


    }
}
