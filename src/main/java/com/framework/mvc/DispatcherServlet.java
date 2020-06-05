package com.framework.mvc;

import com.framework.aop.AspectWeaver;
import com.framework.core.BeanContainer;
import com.framework.inject.annotation.DependencyInjector;
import com.framework.mvc.process.RequestProcessor;
import com.framework.mvc.process.RequestProcessorChain;
import com.framework.mvc.process.impl.ControllerRequestProcessor;
import com.framework.mvc.process.impl.JspRequestProcessor;
import com.framework.mvc.process.impl.PreRequestProcessor;
import com.framework.mvc.process.impl.StaticResourceRequestProcessor;
import com.fuyouj.controller.frontend.MainPageController;
import com.fuyouj.controller.superadmin.HeadLineOperationController;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FuYouJ
 * @date 2020/5/5  21:33
 **/

/**
 * 拦截所有的路由请求， @WebServlet("/")
 * 解析请求
 * 将请求派发给Controller的方法 去处理
 * /* 拦截所有请求
 */

/**
 * 分发器
 */
@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    List<RequestProcessor> PROCESSOR = new ArrayList<>();
    @Override
    public void init() throws ServletException {
       //初始化常驻内存的应用
        //1.初始化容器和AOP容器
        initIOC(false);;
        //2.初始化请求处理器责任链
        initProcessor();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.创建责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(),req,resp);
        //2.通过责任链模式依次调用请求处理器进行处理
        requestProcessorChain.doRequestProcessorChain();
        //3.对处理结果进行渲染
        requestProcessorChain.doRender();
    }

    private void initProcessor() {
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));;
        PROCESSOR.add(new ControllerRequestProcessor());
    }

    private void initIOC(boolean AOP) {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.fuyouj");
        if (AOP){
            new AspectWeaver().doAopByAspectJ();
        }
        new DependencyInjector().doIoc();
    }
}
