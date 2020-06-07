package com.fuyouj.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/5 22:13
 */
public class GetSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private GetSessionFactory(){

    }
    synchronized public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory==null){
            String resources="mybatis-config.xml";
            InputStream inputStream=null;
            try {
                inputStream= Resources.getResourceAsStream(resources);
            }catch (Exception e){
                e.printStackTrace();
            }
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;

    }
}