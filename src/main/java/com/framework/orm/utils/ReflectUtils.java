package com.framework.orm.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/6 0:34
 */
public class ReflectUtils {
    /**
     * 调用get方法
     * @param filedName
     */
    public static Object invokeGet(String filedName,Object o){
        try {
            Class<?> clazz = o.getClass();
            Method method = clazz.getMethod("get" + StringUtils.firstUpper(filedName),null);
            Object res = method.invoke(o, null);
            return res;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void invokeSet(Object rowObject,String columnName,Object columnValue){
        try {
            Class<?> clazz = rowObject.getClass();
            Method method = clazz.getDeclaredMethod("set" + StringUtils.firstUpper(columnName),
                    columnValue.getClass());
            method.invoke(rowObject,columnValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}