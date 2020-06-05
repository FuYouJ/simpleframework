package com.framework.aop;

import com.framework.core.BeanContainer;
import com.framework.inject.annotation.DependencyInjector;
import com.fuyouj.controller.TestAopController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/2 0:44
 */
public class TestAop {
    @DisplayName("测试第二个版本的AOP")
    @Test
    public void testAopOne(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        //加载了所有类
        beanContainer.loadBeans("com.fuyouj");
        //AOP
        new AspectWeaver().doAopByAspectJ();
        // ioc
        new DependencyInjector().doIoc();
        TestAopController controller = (TestAopController) beanContainer.getBean(TestAopController.class);
        int say = controller.say();
        System.out.println("======================================返回结果======="+say+"======");
    }
}