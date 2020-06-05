package com.framework.mvc.render.impl;


import com.framework.mvc.process.RequestProcessorChain;
import com.framework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @Desc 找不到资源而抛出的异常
 * @Author FuYouJ
 * @date 2020/6/3 23:01
 */

public class ResourceNotFoundResultRender implements ResultRender {
    private String httpMethod;
    private String httpPath;
    public ResourceNotFoundResultRender(String requestMethod, String requestPath) {
        this.httpMethod = requestMethod;
        this.httpPath = requestPath;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND
                ,"获取不到对应的资源:请求路径 ["+httpPath +"] 请求方法 ["+httpMethod+"]"
                );
    }
}