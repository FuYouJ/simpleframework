package com.framework.orm.core;

/**
 * @Desc 根据配置信息创建query对象 工厂模式
 * @Author FuYouJ
 * @date 2020/6/6 0:26
 */
public class QueryFactory {
    private static Query prototype;
    private static Class<?> clazz;
    //懒汉模式
    private static QueryFactory queryFactory = new QueryFactory();
    private QueryFactory (){}
    static {
        try {
        String queryClass = DBManager.getConfiguration().getQueryClass();
        clazz = Class.forName(queryClass);
        prototype = (Query) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Query createQuery(){
        try {
            return (Query) prototype.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}