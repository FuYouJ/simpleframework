package com.framework.orm.utils;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/6 0:34
 */
public class StringUtils {
    public static void main(String[] args) {

    }
    /**
     * 首字母大写
     */
    public static String firstUpper(String s){
        String upperCase = s.toUpperCase();
        String first = upperCase.substring(0, 1);
        String res = first + s.substring(1);
        return res;
    }
}