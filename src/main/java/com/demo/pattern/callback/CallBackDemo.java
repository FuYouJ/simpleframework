package com.demo.pattern.callback;

public class CallBackDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("休眠");
                    //模拟耗时
                    Thread.sleep(2000);
                    System.out.println("我回调了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}