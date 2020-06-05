package com.framework.inject.annotation;

/**
 * @author FuYouJ
 * @date 2020/5/21  13:54
 **/

import com.framework.core.BeanContainer;
import com.framework.core.util.ClassUtil;
import com.framework.core.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import sun.awt.image.ImagingLib;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * 依赖注入的服务
 */
@Slf4j
public class DependencyInjector {
    private BeanContainer beanContainer;
    public DependencyInjector(){
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IOC
     */
    public void doIoc(){
        //遍历容器class对象
        if (ValidationUtil.isEmpty(beanContainer.getClasses())){
            log.warn("容器为空");
            return;
        }
        for (Class<?> clazz : beanContainer.getClasses()) {
            //遍历每个对象的 成员变量
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)){
                continue;
            }
            //找到自动注入的注解
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();
                    //获取类型
                    Class<?> fieldClass = field.getType();
                    //再容器中找到
                    Object fieldValue =  getFiledInstance(fieldClass,autowiredValue);
                    if (fieldValue == null){
                        throw new RuntimeException("注入失败，容器中不存在该类型，名字："+autowiredValue);
                    }else {
                        //注入
                        Object targetBean = beanContainer.getBean(clazz);
                        //通过反射注入
                        ClassUtil.setField(field,targetBean,fieldValue,true);
                    }
                }
            }
        }
    }

    /**
     * 根据class对象 获取容器管理的类或者实现类
     * @param fieldClass
     * @param autowired
     * @return
     */
    private Object getFiledInstance(Class<?> fieldClass, String autowired) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        //入如果直接获取到了就返回
        if (fieldValue != null) {
            return fieldValue;
        }else {
            //找实现类
             Class<?> implClass =  getImplementClass(fieldClass,autowired);
             if (implClass != null){
                 return beanContainer.getBean(implClass);
             }else {
                 return null;
             }
        }
    }

    /**
     * 获取接口的实现类
     * @param fieldClass
     * @param autowired
     * @return
     */
    private Class<?> getImplementClass(Class<?> fieldClass, String autowired) {
        Set<Class<?>> classsSet = beanContainer.getClassesBySuper(fieldClass);
        if (ValidationUtil.isEmpty(classsSet) == false){
            //如果有多个实现类呢？ 通过 Qualifier 设置名称
            if (ValidationUtil.isEmpty(autowired)){
                //说明没有精确指定
                if (classsSet.size() == 1){
                    return classsSet.iterator().next();
                }else {
                    //抛出异常
                    throw  new RuntimeException("发现多个继承类！却又没有指定名字"+fieldClass.getName());
                }
            }else {
                for (Class<?> clazz : classsSet) {
                    //如果类的类名等于指定的名字
                    if (autowired.equals(clazz.getSimpleName())){
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
