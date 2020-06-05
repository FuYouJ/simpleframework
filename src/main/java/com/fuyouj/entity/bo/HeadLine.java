package com.fuyouj.entity.bo;

import com.framework.core.util.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;

/**
 * @author FuYouJ
 * @date 2020/4/23  14:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadLine {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("***");
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.fuyouj.entity");
        System.out.println(classSet);
        System.out.println("?????");
    }
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastsEditTime;
}
