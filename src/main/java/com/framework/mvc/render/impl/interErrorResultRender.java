package com.framework.mvc.render.impl;

import com.framework.mvc.process.RequestProcessorChain;
import com.framework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Desc 内部异常渲染器
 * @Author FuYouJ
 * @date 2020/6/3 23:00
 */

public class interErrorResultRender implements ResultRender {
    private String errorMsg;
    public interErrorResultRender(String errorMsg){
        this.errorMsg = errorMsg;
    }
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsg);
    }
}