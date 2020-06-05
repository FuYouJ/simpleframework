package com.fuyouj.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FuYouJ
 * @date 2020/4/23  15:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    //本次请求返回的结果集
    private T data;
}
