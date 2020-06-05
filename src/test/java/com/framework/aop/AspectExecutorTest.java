package com.framework.aop;

import com.framework.aop.mock.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 22:48
 */
public class AspectExecutorTest {
    @Test
    @DisplayName("排序切面")
   void sortTest(){
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        aspectInfoList.add(new AspectInfo(3,new Mock1()));
        aspectInfoList.add(new AspectInfo(5,new Mock2()));
        aspectInfoList.add(new AspectInfo(2,new Mock3()));
        aspectInfoList.add(new AspectInfo(4,new Mock4()));
        aspectInfoList.add(new AspectInfo(1,new Mock5()));
        AspectListExecutor executor = new AspectListExecutor(AspectExecutorTest.class,aspectInfoList);
        executor.getSortedAspectInfoList()
                .forEach(item -> System.out.println(item.getAspectObject().getClass().getName()));
    }
}