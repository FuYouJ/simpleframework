package com.fuyouj.entity.dto;

import com.fuyouj.entity.bo.HeadLine;
import com.fuyouj.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

/**
 * @author FuYouJ
 * @date 2020/4/23  15:30
 **/
@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;
}
