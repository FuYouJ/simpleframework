package com.framework.orm.utils;

import com.framework.orm.bean.ColumnInfo;
import com.framework.orm.bean.JavaFiledGetSet;
import com.framework.orm.bean.TableInfo;
import com.framework.orm.core.DBManager;
import com.framework.orm.core.TableContext;
import com.framework.orm.core.TypeConverter;
import com.framework.orm.core.impl.MySQLTypeConverterImpl;


import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Desc 生成java文件
 * @Author FuYouJ
 * @date 2020/6/6 0:34
 */
public class JavaFileUtils {
    public static void createJavaFile(TableInfo tableInfo,TypeConverter typeConverter, boolean lombok) throws UnsupportedEncodingException {
        String javaSrc = createJavaSrc(tableInfo, typeConverter,lombok);
        //d:\\SRC
        //  String encode = URLDecoder.decode(resource.getPath(), "UTF-8");
        String srcPath = DBManager.getConfiguration().getSrcPath();
        srcPath = URLDecoder.decode(srcPath,"UTF-8");
        String basePath = srcPath+ File.separator;
        String s = DBManager.getConfiguration().getPoPackage().replace(".", File.separator);
        String targetPath = basePath + s;
        File file = new File(targetPath);
        if (file.exists() == false){
            file.mkdirs();
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(targetPath + File.separator + StringUtils.firstUpper(tableInfo.getTableName()) + ".java"));
            bw.write(javaSrc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          if (bw != null){
              try {
                  bw.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
    }
    /**
     * 根据字段信息生成java属性信息 varchar name ->String name
     * @param column 字段
     * @param typeConverter
     * @param lombok 是否使用lombok
     * @return
     */
    private static JavaFiledGetSet createFieldGetSetSRC(ColumnInfo column, TypeConverter typeConverter, boolean lombok)
    {
        JavaFiledGetSet javaFiledGetSet = new JavaFiledGetSet();
        String dataType = column.getDataType();
        String javaType = typeConverter.dataBaseType2JavaType(dataType);
        javaFiledGetSet.setFiledInfo("\tprivate " + javaType +" "+ column.getName() + ";\n");
        if (lombok == false){
            String getMethod = getMethod(column, typeConverter);
            javaFiledGetSet.setGetInfo(getMethod);
            String setMethod = setMethod(column,typeConverter);
            javaFiledGetSet.setSetInfo(setMethod);
        }
        return javaFiledGetSet;
    }

    private static String setMethod(ColumnInfo column, TypeConverter typeConverter) {
        String dataType = column.getDataType();
        String javaType = typeConverter.dataBaseType2JavaType(dataType);
        StringBuilder setSrc = new StringBuilder();
        setSrc.append("\tpublic void set" + StringUtils.firstUpper(column.getName()))
                .append("(")
                .append(javaType +" " + column.getName() + "){\n")
                .append("\t\tthis." + column.getName() + "=" +column.getName() +";\n")
                .append("\t}\n");
        return setSrc.toString();
    }

    private static String getMethod(ColumnInfo column,TypeConverter typeConverter){
        String dataType = column.getDataType();
        String javaType = typeConverter.dataBaseType2JavaType(dataType);
        StringBuilder getSrc = new StringBuilder();
        getSrc.append("\tpublic " + javaType + " get" + StringUtils.firstUpper(column.getName()))
                .append("(){\n")
                .append("\t\treturn ")
                .append(column.getName() + ";\n")
                .append("\t}\n");
        return getSrc.toString();
    }

    /**
     * 根据表信息生成java类 考虑支持lombok
     * @param table
     * @param converter
     * @param lombok 是否使用lombok
     * @return
     */
    public static String createJavaSrc(TableInfo table,TypeConverter converter,boolean lombok){
        StringBuilder res = new StringBuilder();
        Map<String, ColumnInfo> columns = table.getColumns();
        List<JavaFiledGetSet> javaFiledList = new ArrayList<>();
        //遍历所有字段 并且生成
        for (ColumnInfo value : columns.values()) {
            javaFiledList.add(createFieldGetSetSRC(value,converter,lombok));
        }
        //生成包命名空间
        res.append("package "+DBManager.getConfiguration().getPoPackage() + ";\n\n");
        /**
         * import lombok.AllArgsConstructor;
         * import lombok.Data;
         * import lombok.NoArgsConstructor;
         */
        if (lombok){
            String lombokStr = "import lombok.";
            res.append(lombokStr).append("AllArgsConstructor;\n");
            res.append(lombokStr).append("Data;\n");
            res.append(lombokStr).append("NoArgsConstructor;\n");
        }
        //import
//        res.append("import java.sql.*;\n");
        res.append("import java.util.*;\n\n");
        /**
         * @Desc 生成java文件
         * @Author FuYouJ
         * @date 2020/6/6 0:34
         */
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy/MM/dd hh:mm").format(date);
        //类声明
        res.append("/**\n")
                .append("* @Desc 根据工具类自动生成Java文件\n")
                .append("* @Author FuYouJ\n")
                .append("* @date " + format +"\n")
                .append("*/\n");
        /**
         * @Data
         * @AllArgsConstructor
         * @NoArgsConstructor
         */
        if (lombok){
            res.append("@Data\n");
            res.append("@AllArgsConstructor\n");
            res.append("@NoArgsConstructor\n");
        }
        res.append("public class "+ StringUtils.firstUpper(table.getTableName()) + " {\n");


        //生成属性列表
        for (JavaFiledGetSet javaFiled : javaFiledList) {
            res.append(javaFiled.getFiledInfo()+"\n");
        }
        //get set
        if (lombok == false){
            res.append("\n\n");
            for (JavaFiledGetSet javaFiled : javaFiledList) {
                res.append(javaFiled.getGetInfo());
            }
            for (JavaFiledGetSet javaFiled : javaFiledList) {
                res.append(javaFiled.getSetInfo());
            }
        }
        //类结束
        res.append("}\n");
        return res.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
/*        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setName("username");
        columnInfo.setKeyType(2);
        columnInfo.setDataType("bigint");
        TypeConverter typeConverter = new MySQLTypeConverterImpl();
        String method = getMethod(columnInfo, typeConverter);
      *//*  System.out.println(method);
        System.out.println(setMethod(columnInfo, typeConverter));*//*
        JavaFiledGetSet src = createFieldGetSetSRC(columnInfo, typeConverter,false);
        src.toString();*/
        Map<String, TableInfo> tables = TableContext.tables;
        //获取 exam表
//        TableInfo tableInfo = tables.get("exam");
        TypeConverter typeConverter = new MySQLTypeConverterImpl();
//        createJavaFile(tableInfo,typeConverter,true);
        for (TableInfo value : tables.values()) {
            System.out.println("生成:"+value.getTableName());
            createJavaFile(value,typeConverter,true);
        }
    }
}