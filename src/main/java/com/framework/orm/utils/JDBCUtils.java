package com.framework.orm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Desc 封装JDBC查询的常用操作
 * @Author FuYouJ
 * @date 2020/6/6 0:33
 */
public class JDBCUtils {
    public static void handle(PreparedStatement ps,Object[] params) throws SQLException {
        if (params != null){
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1,params[i]);
            }
        }
    }
}