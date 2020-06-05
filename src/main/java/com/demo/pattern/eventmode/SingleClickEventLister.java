package com.demo.pattern.eventmode;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/26 16:20
 */
public class SingleClickEventLister implements EventLister {

    @Override
    public void processEvent(Event event) {
        if ("单击".equals(event.getType())){
            System.out.println("单击事件被触发");
        }
    }
}