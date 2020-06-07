package com.framework.orm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.midi.Soundbank;

/**
 * @Desc 封装java属性和 getset方法
 * @Author FuYouJ
 * @date 2020/6/6 12:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JavaFiledGetSet {
    /**
     * 属性的源码信息，如 private int  id
     */
    private String filedInfo;
    private String getInfo;
    private String setInfo;
    @Override
    public String toString(){
        System.out.println(filedInfo);
        System.out.println(getInfo);
        System.out.println(setInfo);
        return super.toString();
    }
}