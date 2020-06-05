package com.framework.aop;

import com.framework.aop.annotation.Aspect;
import com.framework.aop.annotation.Order;
import com.framework.aop.aspect.DefaultAspect;
import com.framework.core.BeanContainer;
import com.framework.core.util.ValidationUtil;


import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/1 13:25
 */


public class AspectWeaver {
    private BeanContainer beanContainer;
    public AspectWeaver(){
        beanContainer = BeanContainer.getInstance();
    }
    private   void doAop(){
        //1.获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);

        if (ValidationUtil.isEmpty(aspectSet)){ return; }
        //2.将切面类按照不同的织入目标进行切分
        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
        for (Class<?> aspectClass : aspectSet) {
            boolean b =  verifyAspect(aspectClass);
            if (b){
                categorizeAspect(categorizedMap,aspectClass);
            }else {
                throw  new RuntimeException("当前切面类必须持有@Aspect 和 @Order 和继承自DefaultAspect");
            }
        }
        //3.按照切面目标器按序织入逻辑
        if (ValidationUtil.isEmpty(categorizedMap)){return;}
        for (Class<? extends Annotation> aClass : categorizedMap.keySet()) {
            //执行织入 代理对象替换了容器的被代理对象
            List<AspectInfo> aspectInfoList = categorizedMap.get(aClass);
            weaveByCategory(aClass, aspectInfoList);
        }
    }
    /**
     * 复用AspectJ的解析能力
     */
    public void doAopByAspectJ(){
        //1.获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)){return;}
        //将切面类按照不同的织入目标进行切分
        //初始化切面描述类集合
         List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);

        //遍历容器的类
        Set<Class<?>> classes = beanContainer.getClasses();
        for (Class<?> targetClass : classes) {
            //排除切面类自己，不能切自己会死循环
            if (targetClass.isAnnotationPresent(Aspect.class)){
                continue;
            }
            //初筛  判断这些切面类的是否是为这个类服务的 留下每个类粗筛后的结果
            List<AspectInfo> roughMatchAspectList =  collectRoughMatchedAspectListForSpecificClass(aspectInfoList,targetClass);
            //对每个类粗筛后的 尝试织入
            wrapIfNecessary(roughMatchAspectList,targetClass);
        }
    }

    /**
     * 尝试织入逻辑
     * @param roughMatchAspectList
     * @param targetClass
     */
    private void wrapIfNecessary(List<AspectInfo> roughMatchAspectList, Class<?> targetClass) {
        if (ValidationUtil.isEmpty(roughMatchAspectList)){
            return;
        }
        //创建动态代理对象
        AspectListExecutor executor = new AspectListExecutor(targetClass,roughMatchAspectList);
        Object proxyBean = ProxyCreator.createProxy(targetClass, executor);
        //替换被代理的对象
        beanContainer.addBean(targetClass,proxyBean);
    }

    /**
     * 初筛
     * @param aspectInfoList
     * @param targetClass
     * @return 返回初筛后的列表
     */
    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchAspectList = new ArrayList<>();
        if (ValidationUtil.isEmpty(aspectInfoList)){return roughMatchAspectList;}
        for (AspectInfo aspectInfo : aspectInfoList) {
            //初筛
            boolean matches = aspectInfo.getPointCutLocator().roughMatches(targetClass);
            //如果匹配 就加入
            if (matches){
                roughMatchAspectList.add(aspectInfo);
            }
        }
        return roughMatchAspectList;
    }

    /**拼接切面描述类List
     * @param aspectSet
     * @return
     */
    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)){
                Order orderTag = aspectClass.getAnnotation(Order.class);
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                //初始化表达式定位器
                PointCutLocator pointCutLocator = new PointCutLocator(aspectTag.pointCut());
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(),defaultAspect,pointCutLocator);
                aspectInfoList.add(aspectInfo);
            }else {
                throw  new RuntimeException("当前切面类必须持有@Aspect和 @Order 和继承自DefaultAspect,且符合规范");
            }
        }
        return aspectInfoList;
    }


    /**
     *
     * @param aspectAnnotationClass 要被切的注解所对应的类 比如@service的xxxxService
     * @param aspectInfos 他所对应的集合
     */
    private void weaveByCategory(Class<? extends Annotation> aspectAnnotationClass, List<AspectInfo> aspectInfos) {
     //获取被例如 @service标记的所有类
        Set<Class<?>> aspectClassSet = beanContainer.getClassesByAnnotation(aspectAnnotationClass);
        if (ValidationUtil.isEmpty(aspectClassSet)){return;}
        //遍历 为每个被代理的类生成动态代理对象
        for (Class<?> targetClass : aspectClassSet) {
            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,aspectInfos);
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
            //将代理对象放到容器 替换被代理的实例
            beanContainer.addBean(targetClass,proxyBean);
        }
    }

    /**
     *将切面类 按照不同的切面目标进行切分
     * @param categorizedMap
     * @param aspectClass
     */
    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
        DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(),aspect);
        if (categorizedMap.containsKey(aspectTag.value()) == false) {
            //如果第一次出现
            List<AspectInfo> aspectInfoList = new ArrayList<>();
            aspectInfoList.add(aspectInfo);
            categorizedMap.put(aspectTag.value(),aspectInfoList);
        }else {
            //已有列表
            categorizedMap.get(aspectTag.value())
                    .add(aspectInfo);
        }
    }

    /**
     * 验证切面 类是否满足要求
     * 必须要有两个切面标签
     * 必须继承自DefaultAspect.class
     * Aspect的属性不能是他本身
     * @param aspectClass
     * @return
     */
    private boolean verifyAspect(Class<?> aspectClass) {
       /* //注解形式
        if (isDefaultAspect(aspectClass)){
            return aspectClass.isAnnotationPresent(Aspect.class)
                    && aspectClass.isAnnotationPresent(Order.class)
                    && DefaultAspect.class.isAssignableFrom(aspectClass)
                    && aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
        } else {
            //表达式形式
            return aspectClass.isAnnotationPresent(Aspect.class)
                    && aspectClass.isAnnotationPresent(Order.class)
                    && DefaultAspect.class.isAssignableFrom(aspectClass);
        }*/
        return aspectClass.isAnnotationPresent(Aspect.class)
                && aspectClass.isAnnotationPresent(Order.class)
                && DefaultAspect.class.isAssignableFrom(aspectClass);
    }

    /**
     * 校验 切面类遵循哪种规则
     * 式注解还是表达式
     * @param aspectClass
     * @return
     */
    private boolean isDefaultAspect(Class<?> aspectClass){
        if ( ! aspectClass.getAnnotation(Aspect.class).value().getName()
                .equals(DefaultAspect.class.getName())){
            return false;
        }else {
            return true;
        }
    }
}