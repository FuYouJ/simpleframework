package com.framework.mvc.process.impl;

import com.framework.mvc.process.RequestProcessor;
import com.framework.mvc.process.RequestProcessorChain;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/3 22:20
 */

/**
 * 负责对静态资源请求的处理
 * 遇到静态资源 转发给tomcat的   defaultServlet
 */
@Slf4j
public class StaticResourceRequestProcessor implements RequestProcessor {
    private RequestDispatcher defaultDispatcher;
    private final String Default_TOMCAT_SERVLET = "default";
    private final String STATIC_RESOURCE_PREFIX = "/static/";

    public StaticResourceRequestProcessor(ServletContext servletContext) {
        //使用默认的处理器
        this.defaultDispatcher = servletContext.getNamedDispatcher(Default_TOMCAT_SERVLET);
        if (this.defaultDispatcher == null){
            throw new RuntimeException("获取Tomcat默认servlet失败");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //1.判断是否是静态资源 跪地给静态资源的位置 webapp/static
        if (StaticResource(requestProcessorChain.getRequestPath()) == true){
            //是静态资源 就转发给tomcat的default处理
            defaultDispatcher.forward(
                    requestProcessorChain.getRequest(),



                    requestProcessorChain.getResponse()
            );
            //不需要后序处理了
            return false;
        }
        return true;
    }

    /**
     * 判断是不是静态资源路径
     * @param requestPath
     * @return
     */
    private boolean StaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX);
    }
}