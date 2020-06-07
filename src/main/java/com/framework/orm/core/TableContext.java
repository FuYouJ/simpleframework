package com.framework.orm.core;

import com.framework.orm.bean.ColumnInfo;
import com.framework.orm.bean.TableInfo;
import com.framework.orm.core.impl.MySQLTypeConverterImpl;
import com.framework.orm.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.framework.orm.utils.JavaFileUtils.createJavaFile;

/**
 * @Desc 根据表结构生成实体类
 * @Author FuYouJ
 * @date 2020/6/6 0:31
 */
public class TableContext {
    /**
     * 表名为key,表信息为value
     */
    public static Map<String, TableInfo> tables = new HashMap<>();
    /**
     * 类 与 表关联起来
     */
    public static Map<Class<?>,TableInfo> poTables = new HashMap<>();
    private TableContext(){}
    static {
        //初始化获得表信息
        try {
            Connection connection = DBManager.getConn();
            //获取数据库的元信息
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tableRet = metaData.getTables(null,null,
                    null,new String[]{"TABLE"});
            while (tableRet.next()){
                String tableName = (String) tableRet.getObject("TABLE_NAME");
                TableInfo tableInfo = new TableInfo(tableName,new ArrayList<ColumnInfo>(),
                        new HashMap<String,ColumnInfo>());
                tables.put(tableName,tableInfo);
                //查询表中所有字段
                ResultSet set = metaData.getColumns(null,"%",tableName,"%");
                while (set.next()){
                    ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),
                            set.getString("TYPE_NAME"),0);
                    tableInfo.getColumns().put(set.getString("COLUMN_NAME"),ci);
                }
                //查询表的主键
                ResultSet result = metaData.getPrimaryKeys(null,"%",tableName);
                while (result.next()){
                    //获取主键信息
                    ColumnInfo columnInfo = tableInfo.getColumns().get(result.getObject("COLUMN_NAME"));
                    columnInfo.setKeyType(1);
                    //添加主键
                    tableInfo.getPrimaryKeys().add(columnInfo);
                }
                //取唯一主键 方便使用
                if(tableInfo.getPrimaryKeys().size() > 0){
                    //设置唯一主键
                    tableInfo.setPrimaryKey(tableInfo.getPrimaryKeys().get(0));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static Map<String,TableInfo> getTableInfos(){
        return tables;
    }
    public static void loadPoTables(){
        for (TableInfo tableInfo : tables.values()) {
            try {
                Class<?> aClass = Class.forName(DBManager.getConfiguration().getPoPackage()
                        + "." + StringUtils.firstUpper(tableInfo.getTableName()));
                poTables.put(aClass,tableInfo);
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }
    /**
     * 自动生成表对应的Java
     */
    public static void createEntity(){
        Map<String, TableInfo> tables = TableContext.tables;
        TypeConverter typeConverter = new MySQLTypeConverterImpl();
        for (TableInfo value : tables.values()) {
            System.out.println("生成:"+value.getTableName());
            try {
                createJavaFile(value,typeConverter,true);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        loadPoTables();
    }

    public static void main(String[] args) {
        Map<String, TableInfo> tableInfos = getTableInfos();
        System.out.println(tableInfos);
    }
}