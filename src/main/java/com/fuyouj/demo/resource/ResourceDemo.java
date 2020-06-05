package com.fuyouj.demo.resource;

import java.io.*;

/**
 * @author FuYouJ
 * @date 2020/5/22  16:43
 **/
public class ResourceDemo {
    public static void main(String[] args) throws IOException {
        //读取文件 改变内容
        File file = new File("E:\\mooc\\手写Spring源码\\simpleframework\\src\\main\\java\\com\\fuyouj\\demo\\resource\\test.txt");
        System.out.println(file.length());
        OutputStream outputStream = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write("hello world");
        writer.flush();
        writer.close();
        outputStream.close();
    }
}
