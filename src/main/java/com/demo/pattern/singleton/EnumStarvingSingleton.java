package com.demo.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author FuYouJ
 * @date 2020/5/10  14:58
 **/
//超级单例模式 饿汉模式
public class EnumStarvingSingleton {
    //内部攻击
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //其实可以外部反射获取
        Class clazz = ContainerHolder.class;
        //找不到对应无参的构造函数
        /**
         *   protected Enum(String name, int ordinal) {
         *         this.name = name;
         *         this.ordinal = ordinal;
         *     }
         */
        //枚举源码
        //: Cannot reflectively create enum objects
        Constructor constructor = clazz.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        System.out.println(EnumStarvingSingleton.getInstance());
        System.out.println(constructor.newInstance());
    }
    //可以抵挡很多侵入方式 （不能：反射，序列化 ）
    private EnumStarvingSingleton(){}
    //从枚举中获取
    public static EnumStarvingSingleton getInstance(){
        return ContainerHolder.HOLDER.instance;
    }
    private  enum ContainerHolder{
        HOLDER;
        private EnumStarvingSingleton instance;
        private ContainerHolder(){
            instance = new EnumStarvingSingleton();
        }
    }
}
