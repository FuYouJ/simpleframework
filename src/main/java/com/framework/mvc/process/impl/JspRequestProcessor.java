package com.framework.mvc.process.impl;

import com.framework.mvc.process.RequestProcessor;
import com.framework.mvc.process.RequestProcessorChain;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/3 22:21
 */

/**
 * 负责对不经过控制器转发的JSP页面的处理
 */
public class JspRequestProcessor implements RequestProcessor {
    //处理jsp的 dispatcher 的名称
    private static final String JSP_SERVLET = "jsp";
    //定义jsp路径地址
    private static final String JSP_RESOURCE_PREFIX = "/templates/";
    //处理jsp资源的处理器
    private RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext) {
        jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (jspServlet == null){
            throw new RuntimeException("没有找到jsp servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if (jspResource(requestProcessorChain.getRequestPath()) == true){
            jspServlet.forward(requestProcessorChain.getRequest(),requestProcessorChain.getResponse());
            //不再后序处理
            return false;
        }
        //交给后续处理
        return true;
    }

    /**
     * 判断是否是jsp资源
     * @param requestPath
     * @return
     */
    private boolean jspResource(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_PREFIX);
    }
}