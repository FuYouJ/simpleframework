package com.fuyouj.service.combine.impl;

import com.framework.core.annotation.Service;
import com.framework.inject.annotation.Autowired;
import com.fuyouj.entity.bo.HeadLine;
import com.fuyouj.entity.bo.ShopCategory;
import com.fuyouj.entity.dto.MainPageInfoDTO;
import com.fuyouj.entity.dto.Result;
import com.fuyouj.service.combine.HeadLineShopCategoryCombineService;
import com.fuyouj.service.solo.HeadLineService;
import com.fuyouj.service.solo.ShopCategoryService;

import java.util.List;

/**
 * @author FuYouJ
 * @date 2020/4/23  15:32
 **/
@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        //获取头条列表
        HeadLine headLineCCondition = new HeadLine();
        headLineCCondition.setEnableStatus(1);
        Result<List<HeadLine>> headlineResult = headLineService.queryHeadline(headLineCCondition, 1, 4);
        //获取店铺类别列表
        ShopCategory shopCategoryCondition = new ShopCategory();
        //获取所有的店铺类别信息
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);
        //合并两者并且返回
        Result<MainPageInfoDTO> result =  mergeMainPageInfoResult(headlineResult,shopCategoryResult);
        return null;
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headlineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return null;
    }
}
