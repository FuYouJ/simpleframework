package com.framework.orm.bean;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Desc 封装表中一个字段的信息
 * @Author FuYouJ
 * @date 2020/6/6 0:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {
    //字段名称
    private String name;
    //字段类型
    private String dataType;
    //键类型  1，主键 2.外键 0 普通
    private int keyType;
}