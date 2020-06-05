package com.demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author FuYouJ
 * @date 2020/5/7  13:34
 **/
public class ConstructCollector {
    /**
     * 获取 构造方法
     *
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取Class对象
        Class<?> name = Class.forName("com.demo.reflect.ReflectTarget");
         //获取所有的公共构造方法
        System.out.println("所有公共构造方法");
        Constructor<?>[] conArray = name.getConstructors();
        for (Constructor<?> c : conArray) {
            System.out.println(c);
        }
        //获取所有构造方法
        System.out.println("所有公共构造方法");
        conArray = name.getDeclaredConstructors();
        for (Constructor<?> c : conArray) {
            System.out.println(c);
        }
        //获取单个参数的构造方法
        System.out.println("获取单个参数的公共构造方法");
        Constructor<?> con = name.getConstructor(String.class, int.class);
        System.out.println("con=" +con);
        //获取单个的私有构造方法
        con = name.getDeclaredConstructor(int.class);
        System.out.println("con=" +con);
        System.out.println("调用私有的构造函数");
        //设置访问权限
        con.setAccessible(true);
        ReflectTarget reflectTarget = (ReflectTarget) con.newInstance(1);
    }
}
