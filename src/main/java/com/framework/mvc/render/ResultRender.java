package com.framework.mvc.render;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/3 22:57
 */

import com.framework.mvc.process.RequestProcessorChain;

/**
 * 渲染请求结果
 */
public interface ResultRender {
    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}