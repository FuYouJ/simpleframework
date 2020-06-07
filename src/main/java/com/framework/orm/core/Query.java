package com.framework.orm.core;

import com.framework.orm.bean.ColumnInfo;
import com.framework.orm.bean.TableInfo;
import com.framework.orm.utils.JDBCUtils;
import com.framework.orm.utils.ReflectUtils;
import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc 查询接口定义
 * @Author FuYouJ
 * @date 2020/6/5 23:41
 */
public abstract class Query implements Cloneable {
    /**
     * 查询模板
     * @param sql
     * @param params
     * @param clazz
     * @param callback
     * @return
     */
    public Object executeQueryTemplate(String sql,Object[]params,Class<?> clazz,CallBackF callback){
        Connection connection = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handle(ps,params);
            resultSet =  ps.executeQuery();
            //
          return callback.doExecute(connection,ps,resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            DBManager.close(connection);
        }
    }
    /**
     * 执行一个DML语句
     * @param sql sql语句
     * @param params 参数
     * @return 影响记录的行数
     */
   public int executeDML(String sql,Object[] params){
       Connection connection = DBManager.getConn();
       int count = 0;
       PreparedStatement ps = null;
       try {
           ps = connection.prepareStatement(sql);
           System.out.println(ps);
           JDBCUtils.handle(ps,params);
           count = ps.executeUpdate();
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }finally {
           DBManager.close(connection);
       }
       return count;
    };
    //插入
   public void insert(Object object){
        Class<?> clazz   = object.getClass();
        TableInfo tableInfo = TableContext.poTables.get(clazz);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into "+tableInfo.getTableName() +" (");
        //不为null的属性
        int count = 0;
        //存储参数
        List<Object> params = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = ReflectUtils.invokeGet(fieldName,object);
            if (fieldValue != null){
                count++;
                sql.append(fieldName+",");
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length()-1,')');
        sql.append(" values (");
        for (int i = 0; i <count ; i++) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1,')');
        executeDML(sql.toString(),params.toArray());
    }
    //从数据库中删除某个数据
   public void delete(Object object){
       Class<?> aClass = object.getClass();
       TableInfo tableInfo = TableContext.poTables.get(aClass);
       //获得主键
       ColumnInfo primaryKey = tableInfo.getPrimaryKey();
       //反射调用属性对应的getset
       Object prikeyValue = ReflectUtils.invokeGet(primaryKey.getName(), object);
       deleteByPrimaryKey(aClass,prikeyValue);
   }
    /**
     * 根据主键删除
     * @param clazz 跟表对应的class对象
     * @param id 主键
     */
   public void deleteByPrimaryKey(Class<?> clazz,Object id){
        //通过class对象找到表结构
        TableInfo tableInfo = TableContext.poTables.get(clazz);
        //获得主键
        ColumnInfo primaryKey = tableInfo.getPrimaryKey();
        String sql = "delete from " + tableInfo.getTableName() +" where "
                + primaryKey.getName() +" = ?" ;
        executeDML(sql,new Object[]{id});
    }

    /**
     * 更新 并且只更新指定的属性
     * @param object
     * @return
     */
   public int updateByPrimaryKey(Object object){
        Class<?> clazz = object.getClass();
        List<Object> params = new ArrayList<>();
        TableInfo tableInfo = TableContext.poTables.get(clazz);
        ColumnInfo primaryKey = tableInfo.getPrimaryKey();
        StringBuilder sql = new StringBuilder("update " + tableInfo.getTableName()+" set ");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object get = ReflectUtils.invokeGet(field.getName(), object);
            if (get != null){
                sql.append(field.getName()+"=?,");
                params.add(get);
            }
        }
        sql.setCharAt(sql.length()-1,' ');
        sql.append(" where ");
        sql.append(primaryKey.getName()+"= ? ");
        params.add(ReflectUtils.invokeGet(primaryKey.getName(),object));
        return executeDML(sql.toString(),params.toArray());
    }

    /**
     * 查询返回多行记录
     * @param sql
     * @param clazz 类描述
     * @param params 参数
     * @return
     */
   public List<Object> queryRows(String sql, Class<?> clazz, Object[] params){

       /* PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps=connection.prepareStatement(sql);
            JDBCUtils.handle(ps,params);
            resultSet =  ps.executeQuery();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()){
                if (list == null) {
                    list = new ArrayList<>();
                }
                Object rowObject = clazz.newInstance();
                //select user,name from xx where xx =xx
                for (int i = 0;i < columnCount;i++){
                    String columnName = resultSet.getMetaData().getColumnLabel(i+1);
                    Object columnValue = resultSet.getObject(i+1);
                    //set设置
                    ReflectUtils.invokeSet(rowObject,columnName,columnValue);
                }
                list.add(rowObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return (List<Object>) executeQueryTemplate(sql, params, clazz, new CallBackF() {
           @Override
           public List<Object> doExecute(Connection connection, PreparedStatement ps, ResultSet rs) {
               List res = null;
               try {
                   int columnCount = rs.getMetaData().getColumnCount();
                   while (rs.next()) {
                       if (res == null) {
                           res = new ArrayList<>();
                       }
                       Object rowObject = clazz.newInstance();
                       //select user,name from xx where xx =xx
                       for (int i = 0; i < columnCount; i++) {
                           String columnName = rs.getMetaData().getColumnLabel(i + 1);
                           Object columnValue = rs.getObject(i + 1);
                           //set设置
                           ReflectUtils.invokeSet(rowObject, columnName, columnValue);
                       }
                       res.add(rowObject);
                   }
                   return res;
               } catch (Exception e) {
                   e.printStackTrace();
               }
               return res;
           }
       });
    }

    /**
     * 查询一个记录
     * @param sql
     * @param params 参数
     * @return
     */
   public Object queryValue(String sql,Object[] params){
       /* Connection connection = DBManager.getConn();
        Object res = null;
        PreparedStatement ps;
        ResultSet resultSet;
        try {
            ps = connection.prepareStatement(sql);
            JDBCUtils.handle(ps,params);
            resultSet = ps.executeQuery();
            while (resultSet.next()){
                res = resultSet.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;*/
       return executeQueryTemplate(sql, params, null, new CallBackF() {
           @Override
           public Object doExecute(Connection connection, PreparedStatement ps, ResultSet rs) {
               Object res = null;
               try {
                   while (rs.next()){
                       res = rs.getObject(1);
                   }
               }catch (Exception e){

               }
               return res;
           }
       });
    }

  public   Number queryNumber(String sql,Object[] params){
       return (Number) queryValue(sql,params);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
