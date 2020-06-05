package com.demo.pattern.eventmode;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/26 16:22
 */
public class DoubleClickEventLister implements EventLister {
    @Override
    public void processEvent(Event event) {
        if ("双击".equals(event.getType())){
            System.out.println("双击事件被触发");
        }
    }
}