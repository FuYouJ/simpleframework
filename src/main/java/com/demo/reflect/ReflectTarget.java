package com.demo.reflect;

/**
 * @author FuYouJ
 * @date 2020/5/7  13:17
 **/
public class ReflectTarget {
    protected ReflectTarget(String str){
        System.out.println("（默认）包可见的构造方法");
    }
    public ReflectTarget(){
        System.out.println("共有的无参构造方法");
    }
    public ReflectTarget(char name){
        System.out.println("共有的有参构造函数，参数值为："+name);
    }
    public ReflectTarget(String name,int index){
        System.out.println("多惨："+name+index);
    }
    protected ReflectTarget(boolean n){
        System.out.println("受保护的构造方法"+n);
    }
    private ReflectTarget(int index){
        System.out.println("序号"+index);
    }
    public static void main(String[] args) throws ClassNotFoundException {
        ReflectTarget reflectTarget = new ReflectTarget();
        Class reflectTarget1 = reflectTarget.getClass();
        System.out.println("1st"+reflectTarget1.getName());
        //
        Class<ReflectTarget> aClass = ReflectTarget.class;
        System.out.println("2nd"+aClass.getName());
        //
        System.out.print("是否同一个");
        System.out.println(reflectTarget1 == aClass);
        //
        Class<?> forName = Class.forName("com.demo.reflect.ReflectTarget");
        System.out.println("3rd"+forName.getName());
        System.out.print("是否同一个");
        System.out.println(forName == aClass);
    }
}
