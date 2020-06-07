package com.framework.orm.core.impl;

import com.framework.orm.core.TypeConverter;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc mysql 到 java类型转换
 * @Author FuYouJ
 * @date 2020/6/6 12:26
 */
public class MySQLTypeConverterImpl implements TypeConverter {
    private Map<String,String> db2JavaMap = new HashMap<>();
    public MySQLTypeConverterImpl(){
        //初始化类型参考
        db2JavaMap.put("varchar","String");
        db2JavaMap.put("char","String");
        db2JavaMap.put("int","Integer");
        db2JavaMap.put("tinyint","Integer");
        db2JavaMap.put("integer","Integer");
        db2JavaMap.put("bigint","Long");
        db2JavaMap.put("double","Double");
        db2JavaMap.put("float","Float");
        db2JavaMap.put("clob","java.sql.Clob");
//        db2JavaMap.put("blob","java.sql.Blob");
        db2JavaMap.put("blob","String");
//        db2JavaMap.put("date","java.sql.Date");
        db2JavaMap.put("date","Date");
//        db2JavaMap.put("time","java.sql.Time");
        db2JavaMap.put("time","Date");
        db2JavaMap.put("timestamp","Date");
        db2JavaMap.put("datetime","Date");
        db2JavaMap.put("bit","Boolean");
        db2JavaMap.put("text","String");
    }
    @Override
    public String dataBaseType2JavaType(String columnType) {
        return db2JavaMap.get(columnType.toLowerCase());
       /* if ("varchar".equalsIgnoreCase(columnType) || "char".equalsIgnoreCase(columnType)){
            return "String";
        }else if ("int".equalsIgnoreCase(columnType) ||
                "tinyint".equalsIgnoreCase(columnType) ||
                "smallint".equalsIgnoreCase(columnType)||
                "integer".equalsIgnoreCase(columnType)){
            return "Integer";
        }else if ("bigint".equalsIgnoreCase(columnType)){
            return "Long";
        }else if ("double".equalsIgnoreCase(columnType) || "float".equalsIgnoreCase(columnType)){
            return "Double";
        }else if ("clob".equalsIgnoreCase(columnType)){
            return "java.sql.Clob";
        }else if ("blob".equalsIgnoreCase(columnType)){
            return "java.sql.Blob";
        }else if ("date".equalsIgnoreCase(columnType)){
            return "java.sql.Date";
        }else if ("time".equalsIgnoreCase(columnType)){
            return "java.sql.Time";
        }else if ("timestamp".equalsIgnoreCase(columnType)){
            return "java.sql.TimeStamp";
        }
        return null;*/
    }

    @Override
    public String JavaType2DataBase(String javaType) {
        return null;
    }
}