package com.framework.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author FuYouJ
 * @date 2020/5/11  21:18
 **/
public class ValidationUtil {
    /**
     * 集合判空
     */
    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty() ;
    }
    /**
     * 字符串判空
     */
    public static boolean isEmpty(String obj){
        return obj == null || "".equals(obj);
    }
    /**
     * 数组判空
     */
    public static boolean isEmpty(Object[] obj){
        return obj == null || obj.length == 0;
    }
    /**
     * 图判空
     */
    public static boolean isEmpty(Map<?,?>obj){
        return obj == null || obj.isEmpty();
    }
}
