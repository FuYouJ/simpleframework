package com.framework.core;

import com.framework.core.annotation.Controller;
import com.framework.mvc.DispatcherServlet;
import com.fuyouj.controller.frontend.MainPageController;
import com.fuyouj.service.solo.HeadLineService;
import com.fuyouj.service.solo.impl.HeadLineServiceImpl;
import org.junit.jupiter.api.*;

/**
 * @author FuYouJ
 * @date 2020/5/11  21:27
 **/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {
    private static BeanContainer beanContainer;
    @BeforeAll
    static void init(){
        beanContainer = BeanContainer.getInstance();
    }
    @Test
    @Order(1)
    public void loadBeansTest(){
        Assertions.assertEquals(false,beanContainer.isLoaded());
        beanContainer.loadBeans("com.fuyouj");
       /* for (Object bean : beanContainer.getBeans()) {
            System.out.println(bean.getClass().getName());
        }*/

//        System.out.println(beanContainer.size());
    }
    /**
     * getBean
     */
    @Test
    @Order(2)
    @DisplayName("根据类获取实例：getBeanTest")
    public void getBeansTest(){
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);

        Assertions.assertEquals(true,controller instanceof MainPageController);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) beanContainer.getBean(DispatcherServlet.class);
        Assertions.assertEquals(null,dispatcherServlet);
    }

    @Test
    @Order(3)
    @DisplayName("根据注解获取实例")
    public void getClassByAnnotationTest(){
        Assertions.assertEquals(true,beanContainer.isLoaded());
        Assertions.assertEquals(3,beanContainer.getClassesByAnnotation(Controller.class).size());
    }
    @Test
    @DisplayName("根据接口获取实例")
    @Order(4)
    public void getClassBySuperClass(){
        Assertions.assertEquals(true,beanContainer.isLoaded());
        Assertions.assertEquals(true,beanContainer.getClassesBySuper(HeadLineService.class).contains(HeadLineServiceImpl.class));
    }
}
