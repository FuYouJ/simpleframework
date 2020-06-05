package com.framework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc 封装http请求路径和请求方法
 * @Author FuYouJ
 * @date 2020/6/4 15:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPathInfo {
    // http请求方法
    private String httpMethod;
    //请求路径
    private String httpPath;
}