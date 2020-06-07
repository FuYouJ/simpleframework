package com.framework.orm.core.impl;

import com.framework.orm.bean.ColumnInfo;
import com.framework.orm.bean.TableInfo;
import com.framework.orm.core.DBManager;
import com.framework.orm.core.Query;
import com.framework.orm.core.TableContext;
import com.framework.orm.utils.JDBCUtils;
import com.framework.orm.utils.ReflectUtils;
import com.framework.orm.utils.StringUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/6 18:23
 */
public class MySQLQueryImpl extends Query {
}