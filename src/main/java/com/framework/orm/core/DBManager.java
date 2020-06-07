package com.framework.orm.core;

import com.framework.orm.bean.Configuration;
import com.framework.orm.pool.DBConnPool;
import com.mysql.cj.protocol.Resultset;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

/**
 * @Desc 根据配置信息，维持连接对象的管理
 * @Author FuYouJ
 * @date 2020/6/6 0:32
 */
public class DBManager {
    @Getter
    private static Configuration configuration;
    private static DBConnPool dbConnPool;
    static {
        //夹在指定的配置文件
        Properties properties = new Properties();
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
//            properties.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"), "UTF-8"));
            properties.load(reader);
            configuration = new Configuration();
            configuration.setDriver(properties.getProperty("driver"));
            configuration.setPoPackage(properties.getProperty("poPackage"));
            configuration.setPassword(properties.getProperty("password"));
            configuration.setUrl(properties.getProperty("url"));
            configuration.setUser(properties.getProperty("user"));
            configuration.setSrcPath(new String(properties.getProperty("srcPath").getBytes("ISO-8859-1"),"UTF-8"));
            configuration.setSrcPath(properties.getProperty("srcPath"));
            configuration.setUsingDB(properties.getProperty("usingDB"));
            configuration.setQueryClass(properties.getProperty("queryClass"));
            configuration.setPoolMax(Integer.parseInt(properties.getProperty("poolMax")));
            configuration.setPoolMin(Integer.parseInt(properties.getProperty("poolMin")));
            dbConnPool = DBConnPool.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Connection createConn(){
        try {
            Class.forName(configuration.getDriver());
            //直接建立连接
            return DriverManager.getConnection(configuration.getUrl(),configuration.getUser(),configuration.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void close(ResultSet rs, Statement ps, Connection conn){
        try {
            if (rs != null){
                rs.close();
            }
            if (ps != null){
                ps.close();
            }
            if (conn != null){
              dbConnPool.close(conn);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void close(Connection connection){
        if (connection != null){
            try {
               dbConnPool.close(connection);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Connection getConn() {
        return dbConnPool.getConn();
    }
}