package com.framework.core.util;

import org.junit.Test;


import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * @author FuYouJ
 * @date 2020/5/9  13:23
 **/
public class ClassUtilTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("***");
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.fuyouj.entity");
        System.out.println(classSet);
        System.out.println("?????");
    }
    @Test
   public void extractPackageClassTest() throws UnsupportedEncodingException {
        System.out.println("***");
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.fuyouj.entity");
        System.out.println(classSet);
        System.out.println("?????");
    }
}
