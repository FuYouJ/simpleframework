package com.demo.annotation;

/**
 * @author FuYouJ
 * @date 2020/5/7  15:44
 **/
@CourseInfoAnnotation(courseName = "Java",courseTag = "面试",
        courseProfile = "帮助")
public class Course {
    @PersonInfoAnnotation(name = "付有杰",language = {"Java","C#"})
    private String author;
    @CourseInfoAnnotation(courseName = "方法Java",courseTag = "方法面试",
            courseProfile = "方法帮助")
    public void getCourseInfo(){
    }
}
