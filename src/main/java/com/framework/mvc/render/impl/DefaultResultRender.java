package com.framework.mvc.render.impl;

import com.framework.mvc.process.RequestProcessorChain;
import com.framework.mvc.render.ResultRender;

/**
 * @Desc 默认的渲染器
 * @Author FuYouJ
 * @date 2020/6/3 22:58
 */


public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        int code = requestProcessorChain.getResponseCode();
        requestProcessorChain.getResponse().setStatus(code);
    }
}