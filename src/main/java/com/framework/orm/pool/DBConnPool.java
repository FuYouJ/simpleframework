package com.framework.orm.pool;

import com.framework.core.BeanContainer;
import com.framework.orm.core.DBManager;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc 连接池
 * @Author FuYouJ
 * @date 2020/6/7 14:58
 */
public class DBConnPool {
    public static DBConnPool getInstance(){
        return DBConnPoolHolder.HOLDER.instance;
    }
    private enum  DBConnPoolHolder{
        //一个实例
        HOLDER;
        //一个成员
        private DBConnPool instance;
        private DBConnPoolHolder(){
            instance = new DBConnPool();
        }
    }
    private volatile  List<Connection> pool;
    private static final int POOL_MAX_SIZE = DBManager.getConfiguration().getPoolMax() ;
    private static final int POOL_MIN_SIZE = DBManager.getConfiguration().getPoolMin() ;
    /**
     * 使池的连接达到最小值
     */
    private void initPool(){
        if (pool == null){
            synchronized (DBConnPool.class){
                if (pool == null){
                    pool = new ArrayList<>();
                }
            }
        }
        while (pool.size() < POOL_MIN_SIZE){
            pool.add(DBManager.createConn());
        }
    }
    public DBConnPool(){
        initPool();
    }

    /**
     * 取出连接
     * @return
     */
    public  synchronized Connection getConn(){
        int lastIndex = pool.size()-1;
        Connection connection = pool.get(lastIndex);
        pool.remove(lastIndex);
        return connection;
    }

    /**
     * 将链接放回池子里面
     * @param connection
     */
    public synchronized void close(Connection connection){
        if (pool.size() >=POOL_MAX_SIZE){
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            pool.add(connection);
        }
    }
}