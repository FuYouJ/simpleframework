package com.framework.core;

/**
 * @author FuYouJ
 * @date 2020/5/10  16:54
 **/

import com.framework.aop.annotation.Aspect;
import com.framework.core.annotation.Component;
import com.framework.core.annotation.Controller;
import com.framework.core.annotation.Repository;
import com.framework.core.annotation.Service;
import com.framework.core.util.ClassUtil;
import com.framework.core.util.ValidationUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器类 单例模式  (线程安全，防止反射 ，序列化)
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class BeanContainer {
    //判断实例是否已经被加载过
    private boolean loaded = false;
    private final ConcurrentHashMap<Class<?>,Object> beanMap = new ConcurrentHashMap<>();
    /**
     * 加载 bean的注解列表
     * 当一个类被一下几个注解修饰的时候，那么就归容器管理
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class
            ,Service.class, Repository.class
            ,Aspect.class);
    /**
     * 获取容器实例
     * @return
     */
    public static BeanContainer getInstance(){
        return ContainerHolder.HOLDER.instance;
    }
    private enum  ContainerHolder{
        //一个实例
        HOLDER;
        //一个成员
        private BeanContainer instance;
        private ContainerHolder(){
            instance = new BeanContainer();
        }
    }
    /**
     * 扫描加载所有的bean
     *避免 多线程同时加载
     */
    public synchronized void loadBeans(String packageName){
        if (isLoaded()){
            log.warn("容器已经被加载过了");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("没有提取到任何的类"+packageName);
            return;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                //如果有这个注解
                if (clazz.isAnnotationPresent(annotation)) {
                    //将m目标类本省作为建 ，目标类的实例作为 值，放在容器中
                    beanMap.put(clazz,ClassUtil.newInstance(clazz,true));
                }
            }
        }
        loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    public int size(){
        return beanMap.size();
    }


    //增加一个实例
   public Object addBean(Class<?>clazz, Object bean){
       return beanMap.put(clazz,bean);
    }
    //删除
   public Object remove(Class<?>clazz){
       return beanMap.remove(clazz);
    }

    /**
     * 类获取实例
     * @param clazz
     * @return
     */
   public Object getBean(Class<?>clazz){
        return beanMap.get(clazz);
    }

    /**
     * 获取所有的 beanclss集合
     * @return
     */
    public Set<Class<?>> getClasses(){
        return beanMap.keySet();
    }

    /**
     * 获取所有实例
     * @return
     */
   public Set<Object> getBeans(){
        return new HashSet<>(beanMap.values());
    }

    /**
     * 获取某注解 的class
     * @param annotation
     * @return
     */
  public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation){
       //获取所有class
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)){
            log.warn("容器为空");
            return null;
        }
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if (clazz.isAnnotationPresent(annotation)){
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0?classSet:null;
    }
  public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass){
      //获取所有class
      Set<Class<?>> keySet = getClasses();
      if (ValidationUtil.isEmpty(keySet)){
          log.warn("容器为空");
          return null;
      }
      //通过接口筛选
      Set<Class<?>> classSet = new HashSet<>();
      for (Class<?> clazz : keySet) {
          //判断是否是我的子类
          if (interfaceOrClass.isAssignableFrom(clazz)){
              classSet.add(clazz);
              //TODO
          }
      }
      return classSet.size()>0?classSet:null;
  }
}
