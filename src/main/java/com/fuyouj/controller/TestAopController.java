package com.fuyouj.controller;

import com.framework.aop.annotation.Aspect;
import com.framework.aop.annotation.Order;
import com.framework.core.annotation.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/2 0:38
 */
@Controller
public class TestAopController {
    public int say(){
        System.out.println("我是controller的本地方法");
        return 1/0;
    }


}