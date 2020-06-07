package com.framework.orm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/7 14:06
 */
public interface CallBackF {
   Object doExecute(Connection connection, PreparedStatement ps, ResultSet rs);
}
