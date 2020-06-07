package com.framework.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Desc 回调接口
 * @Author FuYouJ
 * @date 2020/6/7 14:03
 */
public interface CallBackF {
    void doExecute(Connection connection, PreparedStatement ps, ResultSet rs);
}
