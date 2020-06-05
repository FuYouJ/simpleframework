package com.demo.pattern.eventmode;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/26 16:25
 */
public class EventModeDemo {
    public static void main(String[] args) {
        EventSource eventSource = new EventSource();
        SingleClickEventLister singleClickEventLister = new SingleClickEventLister();
        DoubleClickEventLister doubleClickEventLister = new DoubleClickEventLister();
        Event event = new Event();
        event.setType("双击");
        eventSource.register(singleClickEventLister);
        eventSource.register(doubleClickEventLister);
        eventSource.publish(event);
    }
}