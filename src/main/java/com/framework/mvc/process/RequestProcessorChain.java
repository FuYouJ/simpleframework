package com.framework.mvc.process;

import com.framework.aop.aspect.DefaultAspect;
import com.framework.mvc.annotation.ResponseBody;
import com.framework.mvc.render.ResultRender;
import com.framework.mvc.render.impl.DefaultResultRender;
import com.framework.mvc.render.impl.JsonResultRender;
import com.framework.mvc.render.impl.ViewResultRender;
import com.framework.mvc.render.impl.interErrorResultRender;
import com.framework.mvc.type.ControllerMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/3 22:15
 */

/**
 * 1.以责任链模式执行注册的请求处理器
 * 2.委派给特定的render实例对处理后的结果经行渲染
 */
@Getter
@Setter
@Slf4j
public class RequestProcessorChain {
    //请求处理器迭代器
    private Iterator<RequestProcessor> requestProcessorIterator;;
    private HttpServletRequest request;
    private HttpServletResponse response;
    //解析出来的请求方法
    private String requestMethod;
    //请求路径
    private String requestPath;
    //请求后的状态码
    private int responseCode;
    //请求结果渲染器
    private ResultRender resultRender;
    public RequestProcessorChain(Iterator<RequestProcessor> iterator,
                                 HttpServletRequest req, HttpServletResponse resp) {
        this.requestProcessorIterator = iterator;
        this.request = req;
        this.response = resp;
        //获取请求方法
        this.requestMethod = req.getMethod();
        //获取请求路径
        this.requestPath = req.getPathInfo();
        //默认成功处理
        this.responseCode = HttpServletResponse.SC_OK;
    }

    public RequestProcessorChain(Iterator<RequestProcessor> iterator, ServletRequest servletRequest, ServletResponse servletResponse) {
        this.requestProcessorIterator = iterator;
        this.request = (HttpServletRequest) servletRequest;
        this.response = (HttpServletResponse) servletResponse;
        //获取请求方法
        this.requestMethod =((HttpServletRequest) servletRequest).getMethod();
        //获取请求路径
        this.requestPath = ((HttpServletRequest) servletRequest).getPathInfo();
        //默认成功处理
        this.responseCode = HttpServletResponse.SC_OK;
    }

    /**
     * 责任链模式执行请求链
     */
    public void doRequestProcessorChain() {
        //1.通过 代器遍历注册的处理器实现类
        try {
            while (requestProcessorIterator.hasNext()){
                boolean res =  requestProcessorIterator.next()
                        .process(this);
                //2.直到某个返回false位置
                if (res == false){
                    break;
                }
            }
        }catch (Exception e){
            //3.期间出现异常则由内部异常渲染器 处理
            this.resultRender = new interErrorResultRender(e.getMessage());
            log.error("do RequestProcessorChain error:",e);
        }
    }

    /**
     * 渲染请求结果
     */
    public void doRender() {
        //1.如果请求处理器均没有选择合适的渲染器，则使用默认的
        if (resultRender == null){
            resultRender = new DefaultResultRender();
        }
        //2.渲染
        try {
            resultRender.render(this);
        }catch (Exception e){
            log.error("doRender error:",e);
            throw new RuntimeException(e);
        }
    }

    public void setResultRender(Object res, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if (res == null){
            return;
        }
        ResultRender resultRender;
        //判断JSON
        Method invokeMethod = controllerMethod.getInvokeMethod();
        if (invokeMethod.isAnnotationPresent(ResponseBody.class) == true){
            resultRender = new JsonResultRender(res);
        }else {
            resultRender = new ViewResultRender(res);
        }
        requestProcessorChain.setResultRender(resultRender);
    }
}