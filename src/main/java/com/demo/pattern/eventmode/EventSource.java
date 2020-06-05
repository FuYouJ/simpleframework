package com.demo.pattern.eventmode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/26 16:23
 */
public class EventSource {
    private List<EventLister> listerList = new ArrayList<>();
    //注册监听器
    public void register(EventLister lister){
        listerList.add(lister);
    }
    //发布时间
    public void publish(Event event){
        for (EventLister eventLister : listerList) {
            eventLister.processEvent(event);
        }
    }
}