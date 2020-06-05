package com.framework.core.util;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/4 17:44
 */
public class ConvertUtil {
    /**
     * 返回基本类型的空值
     * @param type
     * @return
     */
    public static Object primitiveNull(Class<?> type) {
        if (type == int.class || type == double.class ||
            type == short.class || type == long.class ||
            type == byte.class || type == float.class){
            return 0;
        }
        if (type == boolean.class){
            return false;
        }
        return null;
    }

    /**
     * 有值的转换
     * @param type 参数类型
     * @param requestValue
     * @return
     */
    public static Object convert(Class<?> type, String requestValue) {
        if (isPrimitive(type) == true){
            if (ValidationUtil.isEmpty(requestValue)){
                return primitiveNull(type);
            }
            if (type.equals(int.class) || type.equals(Integer.class)){
                return Integer.parseInt(requestValue);
            }else if (type.equals(double.class) || type.equals(Double.class)){
                return Double.parseDouble(requestValue);
            }else if (type.equals(String.class)){
                return requestValue;
            }else if (type.equals(Float.class) || type.equals(float.class)){
                return Float.parseFloat(requestValue);
            }else if (type.equals(Long.class) || type.equals(long.class)){
                return Long.parseLong(requestValue);
            }else if (type.equals(Boolean.class) || type.equals(boolean.class)){
                return Double.parseDouble(requestValue);
            }else if (type.equals(short.class) || type.equals(Short.class)){
                return Short.parseShort(requestValue);
            }else if (type.equals(Byte.class) || type.equals(byte.class)){
                return Byte.parseByte(requestValue);
            }
            return requestValue;
        }else {
            throw new RuntimeException("不支持的类型转换");
        }
    }

    /**
     * 判断是否可以转换
     * @param type
     * @return
     */
    private static boolean isPrimitive(Class<?> type) {

        return type == boolean.class
                || type == Boolean.class
                || type == double.class
                || type == Double.class
                || type == float.class
                || type == Float.class
                || type == short.class
                || type == Short.class
                || type == int.class
                || type == Integer.class
                || type == long.class
                || type == Long.class
                || type == String.class
                || type == byte.class
                || type == Byte.class
                || type == char.class
                || type == Character.class;
    }


}