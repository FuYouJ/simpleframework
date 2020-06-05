package com.framework.mvc.process.impl;

import com.framework.core.BeanContainer;
import com.framework.core.util.ConvertUtil;
import com.framework.core.util.ValidationUtil;
import com.framework.mvc.annotation.RequestMapping;
import com.framework.mvc.annotation.RequestParam;
import com.framework.mvc.process.RequestProcessor;
import com.framework.mvc.process.RequestProcessorChain;
import com.framework.mvc.render.impl.ResourceNotFoundResultRender;
import com.framework.mvc.type.ControllerMethod;
import com.framework.mvc.type.RequestPathInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc  controller请求处理器
 * @Author FuYouJ
 * @date 2020/6/3 22:21
 */
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {
    //IOC容器
    private BeanContainer beanContainer;
    //请求和controller方法的映射集合
    private Map<RequestPathInfo, ControllerMethod> pathInfoControllerMethodMap = new ConcurrentHashMap<>();
    public ControllerRequestProcessor(){
        beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassesByAnnotation(RequestMapping.class);
        initPathInfoControllerMethodMap(requestMappingSet);
    }
    private void initPathInfoControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if (ValidationUtil.isEmpty(requestMappingSet)){
            return;
        }
        //1.遍历 所有被标记的类，属性值作为 一级路径
        for (Class<?> requestMappingClass : requestMappingSet) {
            RequestMapping requestMapping = requestMappingClass.getAnnotation(RequestMapping.class);

            String basePath = requestMapping.value();
            if (basePath.startsWith("/") == false){
                basePath = "/" + basePath;
            }
            //2. 遍历类里面的方法 ，作为二级路径
            Method[] methods = requestMappingClass.getDeclaredMethods();
            if (ValidationUtil.isEmpty(methods)){
                continue;
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                    String methodPath = mapping.value();
                    if (methodPath.startsWith("/") == false){
                        methodPath = "/" +  methodPath;
                    }
                    String url = basePath + methodPath;
                    //3.解析方法的参数
                    Map<String,Class<?>>  methodParams = new HashMap<>();
                    Parameter[] parameters = method.getParameters();
                    if (ValidationUtil.isEmpty(parameters) == false){
                    for (Parameter parameter : parameters) {
                        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                        //规定必须要有这个 注解
                        if (requestMapping == null){
                            throw new RuntimeException("方法参数必须被RequestParam注解标记");
                        }
                        //值  和 类型
                        methodParams.put(requestParam.value(),parameter.getType());
                        }
                    }
                        //4.建立起来 controller方法 与请求方法和请求路径的映射
                    //TODO
                        String httpMethod = String.valueOf(mapping.method());
                        RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
                        if (pathInfoControllerMethodMap.containsKey(requestPathInfo) ){
                            log.warn("存在相同的路几个定义存在于不同的类和方法里面，旧的会被覆盖");
                        }
                        ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, method, methodParams);
                        pathInfoControllerMethodMap.put(requestPathInfo,controllerMethod);
                    }
                }
            }
    }
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //1.解析HTTP 请求方法 请求路径 获取对应的ControllerMethod实例
        String requestPath = requestProcessorChain.getRequestPath();
        String requestMethod = requestProcessorChain.getRequestMethod();
        RequestPathInfo requestPathInfo = new RequestPathInfo(requestMethod,requestPath);
        ControllerMethod controllerMethod = pathInfoControllerMethodMap.get(requestPathInfo);
        if (controllerMethod == null){
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(requestMethod,requestPath));
            return false;
        }
        //2.解析请求参数，传递给获取到的ControllerMethod去执行
        Object res =  invokeControllerMathod(controllerMethod,requestProcessorChain.getRequest());
        //3.根据解析的结果，选择对应的渲染
        requestProcessorChain.setResultRender(res,controllerMethod,requestProcessorChain);
        return true;
    }

    private Object invokeControllerMathod(ControllerMethod controllerMethod, HttpServletRequest request) {
        //1.从请求里获取GET 或者 POST 的参数名及其对应的值
        Map<String,String> requestParamMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for(Map.Entry<String,String[]> parameter : parameterMap.entrySet()){
            if (ValidationUtil.isEmpty(parameter.getValue()) == false){
                //只支持一个参数对应一个值
                requestParamMap.put(parameter.getKey(),parameter.getValue()[0]);
            }
        }
        //2.根据获取到的请求参数名及其对应的值，以及controllerMethod里面的参数和类型的映射关系，区实例化方法对应的参数
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParameterMap = controllerMethod.getMethodParameters();
        for (String paramName : methodParameterMap.keySet()) {
            Class<?> type = methodParameterMap.get(paramName);
            String requestValue = requestParamMap.get(paramName);
            Object value;
            if (requestValue == null){
                //转换不同的空值
                value =  ConvertUtil.primitiveNull(type);
            }else {
                value = ConvertUtil.convert(type,requestValue);
            }
            methodParams.add(value);
        }
        //3.执行里面的方法
        Object controller = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object res;
        try {
            if (methodParams.size() == 0){
                res = invokeMethod.invoke(controller);
            }else {
                res = invokeMethod.invoke(controller,methodParams.toArray());
            }
        }catch (InvocationTargetException e){
            //调用异常 获取方法抛出的异常
            Throwable targetException = e.getTargetException();
            throw new RuntimeException(targetException);
        }catch (IllegalAccessException e){
            //非法访问异常
            throw new RuntimeException(e);
        }
        return res;
    }
}