package com.framework.aop;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 21:53
 */

import com.framework.core.util.ValidationUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;
import java.util.*;

/**
 * 网被代理的方法添加横切逻辑
 */
@Data
@NoArgsConstructor
public class AspectListExecutor implements MethodInterceptor {
    //被代理的class
    private Class<?> targetClass;
    //切面信息集合 照顾多个AOP的 情况
    private List<AspectInfo> sortedAspectInfoList;
    public AspectListExecutor(Class<?> targetClass,List<AspectInfo> aspectInfos){
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sort(aspectInfos);
    }

    private List<AspectInfo> sort(List<AspectInfo> aspectInfos) {
        //升序排列
       Collections.sort(aspectInfos, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
       return aspectInfos;
    }

    /**
     *
     * @param o 被增强的对象
     * @param method 需要拦截的方法
     * @param args 方法参数
     * @param methodProxy 代理方法
     * @return 返回值
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // 2.0 代码 精确筛选
        collectAccurateMatchAspectList(method);
        Object returnValue = null;
        if (ValidationUtil.isEmpty(sortedAspectInfoList)){
            //就算为空也要执行原来的方法啊
            return methodProxy.invokeSuper(o,args);
        }
        //1.按照order的顺序执行完毕所有切面的before方法
        invokeBeforeAdvices(method,args);
        try {
            //2。 执行被代理的方法
            returnValue = methodProxy.invokeSuper(o,args);
            //3. 如果被代理方法正常返回，按照order的顺序 逆序执行afterReturning
            returnValue =  invokeAfterReturningAdvices(method,args,returnValue);
        }catch (Exception e){
            //执行异常时
            invokeAfterThrowingAdvices(method,args,e);
        }
        return returnValue;
    }

    /**
     * 精确匹配 留下符合精确匹配的结果
     * @param method
     */
    private void collectAccurateMatchAspectList(Method method) {
        if(ValidationUtil.isEmpty(sortedAspectInfoList)){return;}
        Iterator<AspectInfo> iterator = sortedAspectInfoList.iterator();
        while (iterator.hasNext()){
            AspectInfo aspectInfo = iterator.next();
            //区别两种方式
            //如果注解不为空 校验注解
            //精确校验
            if (aspectInfo.getPointCutLocator().accurateMatches(method) == false){
                iterator.remove();
            }
        }
    }

    /**
     * 发生异常的时候执行 降序
     * @param method
     * @param args
     * @param e
     */
    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {
        for (int i = sortedAspectInfoList.size() -1; i >=0 ; i--) {
            sortedAspectInfoList.get(i).getAspectObject()
                    .afterThrowing(targetClass,method,args,e);
        }
    }

    /**
     * 如果代理方法正常返回，降序执行afterAdvice
     * @param method
     * @param args
     * @param returnValue
     * @return
     */
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object res = null;
        for (int i = sortedAspectInfoList.size()-1; i >=0 ; i--) {
           res =  sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return res;
    }

    /**
     * 按照oder的 顺序升序执行
     * @param method
     * @param args
     */
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo info : sortedAspectInfoList) {
            info.getAspectObject().before(targetClass,method,args);
        }
    }
}