package com.framework.orm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc 管理配置信息
 * @Author FuYouJ
 * @date 2020/6/6 0:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    private String driver;
    private String url;
    private String user;
    private String password;
    //使用的数据库
    private String usingDB;
    //项目源码的路径 D：//
    private String srcPath;
    //生成的包
    private String poPackage;
    private String queryClass;
    /**
     * 连接池
     */
    private int poolMin;
    private int poolMax;
}