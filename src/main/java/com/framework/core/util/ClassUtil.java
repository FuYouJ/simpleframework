package com.framework.core.util;

import com.sun.org.apache.regexp.internal.RE;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * @author FuYouJ
 * @date 2020/5/7  17:42
 **/
@Slf4j
public class ClassUtil {
    public static void main(String[] args) throws UnsupportedEncodingException {
        extractPackageClass("com.framework.core.util");
    }
    public static final String FILE = "file";
    /**
     *  获取包下类的集合
     * @param packageName
     * @return
     */
  @SneakyThrows
  public static Set<Class<?>> extractPackageClass(String packageName) {
      //获取类的加载器
      ClassLoader classLoader = getClassLoader();
      //获取到加载的到的资源
      String s = packageName.replace(".", "/");
//      s = URLDecoder.decode(s, "UTF-8");
      URL resource = classLoader.getResource(s);
      //E:\mooc\手写Spring源码\simpleframework\target\classes\com\fuyouj\entity
      if (resource == null){
          log.warn("没任何资源");
          return null;
      }
      //获取资源的集合
      Set<Class<?>> classSet = null;
      //过滤出文件累心那个的资源
      if (resource.getProtocol().equals(FILE)){
          classSet = new HashSet<>();
          //乱码 编码
          String encode = URLDecoder.decode(resource.getPath(), "UTF-8");
          File Directory = new File(encode);
          extractClassFile(classSet,Directory,packageName);
      }
      return classSet;
   }

    /**
     *
     * @param classSet
     * @param fileSource  文件或者目录
     * @param packageName  包名
     */
    private static void extractClassFile(Set<Class<?>> classSet, File fileSource, String packageName) {
        if (fileSource.isDirectory() == false){
            return;
        }
        //如果是文件夹，则获取下面所有文件
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()){
                    return true;
                }else {
                    //获取文件的绝对值路径
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.endsWith(".class")){
                        //加载class文件
                        addToClassSet(absolutePath);
                    }
                }
                return false;
            }

            /**
             * 根据类文件的绝对值路径，获取生成对象，并放取到set中，
             * @param absolutePath
             */
            private void addToClassSet(String absolutePath) {
                //提取类名
                absolutePath =   absolutePath.replace(File.separator,".");
                String  className = absolutePath.substring(absolutePath.indexOf(packageName));
                className = className.substring(0,className.lastIndexOf("."));
                //通过反射机制获取对象并机器如到set中
                Class<?> loadClass = loadClass(className);
                classSet.add(loadClass);
            }
        });
        //遍历这些文件夹
        if (files != null){
            for (File file : files) {
                //递归读取文件
                extractClassFile(classSet,file,packageName);
            }
        }
    }

    /**
     * 获取类加载器
     * @return
     */
   public static ClassLoader getClassLoader(){
       return Thread.currentThread().getContextClassLoader();
   }

    /**
     *
     * @param className 全路径类名
     * @return 获取Class对象
     */
   public static Class<?> loadClass(String className){
       try {
           return Class.forName(className);
       } catch (ClassNotFoundException e) {
           log.warn("加载类错误："+e);
           throw  new RuntimeException(e);
       }
   }

    /**
     *
     * @param clazz
     * @param ac 是否可以支持私有示例创建
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<?> clazz,boolean ac){
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(ac);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("实例化异常"+e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param field  成员变量
     * @param target 目标类实例
     * @param value 成员变量的值
     * @param access  允许
     */
  public static void  setField(Field field,Object target,Object value,boolean access){
      field.setAccessible(access);
      try {
          field.set(target,value);
      } catch (IllegalAccessException e) {
          log.warn("设置类型错误"+e);
      }
  }
}
