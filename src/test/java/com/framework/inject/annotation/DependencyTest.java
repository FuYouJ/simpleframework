package com.framework.inject.annotation;

import com.framework.core.BeanContainer;
import com.fuyouj.controller.frontend.MainPageController;
import com.fuyouj.service.combine.HeadLineShopCategoryCombineService;
import com.fuyouj.service.solo.impl.HeadLineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FuYouJ
 * @date 2020/5/21  14:52
 **/
public class DependencyTest {
    public static void main(String[] args) {
        long milliSecond = 1551798059000L;
        Date date = new Date();
        date.setTime(milliSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(date));
    }
    @Test
    @DisplayName("测试依赖注入")
    void doIocTest(){
        BeanContainer container = BeanContainer.getInstance();
        container.loadBeans("com.fuyouj");
        Assertions.assertEquals(true,container.isLoaded());
         MainPageController mainPageController = (MainPageController) container.getBean(MainPageController.class);
         Assertions.assertEquals(true,mainPageController instanceof MainPageController);
        HeadLineShopCategoryCombineService combineService = mainPageController.getCombineService();
        Assertions.assertEquals(null,combineService);
        new DependencyInjector().doIoc();
//        Assertions.assertNotEquals(null,mainPageController.getCombineService());
        Assertions.assertEquals(true,mainPageController.getCombineService() instanceof HeadLineShopCategoryCombineService
        );
    }
}
