package com.fuyouj.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author FuYouJ
 * @date 2020/4/23  14:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategory {
    private Long shopCategoryId;
    private String ShopCategoryName;
    private String ShopCategoryDesc;
    private String ShopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastsEditTime;
    private ShopCategory parent;
}
