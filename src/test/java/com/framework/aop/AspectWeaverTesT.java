package com.framework.aop;

import com.framework.core.BeanContainer;
import com.framework.inject.annotation.DependencyInjector;
import com.fuyouj.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/1 14:05
 */
public class AspectWeaverTesT {
    @Test
    @DisplayName("测试doAop")
    public void doAopTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        //加载了所有类
        beanContainer.loadBeans("com.fuyouj");
        //AOP
        new AspectWeaver().doAopByAspectJ();
        // ioc
        new DependencyInjector().doIoc();
        //测试一个controller
        HeadLineOperationController controller = (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
//        controller.addHeadLine(null,null);
    }
}