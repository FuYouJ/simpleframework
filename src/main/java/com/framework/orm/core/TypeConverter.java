package com.framework.orm.core;

/**
 * @Desc 负责 java数据类型 和 数据库类型之间的转换
 * @Author FuYouJ
 * @date 2020/6/6 0:27
 */
public interface TypeConverter {
    /**
     *数据库转java
     * @param columnType 数据库的字段类型
     * @return java的数据类型
     */
    String dataBaseType2JavaType(String columnType);
    //java 转 数据库
    String JavaType2DataBase(String javaType);
}
