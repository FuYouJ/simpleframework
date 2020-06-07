package com.framework.orm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc 存表结构信息
 * @Author FuYouJ
 * @date 2020/6/6 0:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    private String tableName;
    /**
     * 所有字段的信息s
     */
    private Map<String,ColumnInfo> columns;
    //有且只有一个主键
    private ColumnInfo primaryKey;

    //联合主键
    private List<ColumnInfo> primaryKeys;

    public TableInfo(String tableName, List<ColumnInfo> columnInfos, HashMap<String, ColumnInfo> stringColumnInfoHashMap) {
        this.tableName = tableName;
        this.primaryKeys = columnInfos;
        this.columns = stringColumnInfoHashMap;
    }
}