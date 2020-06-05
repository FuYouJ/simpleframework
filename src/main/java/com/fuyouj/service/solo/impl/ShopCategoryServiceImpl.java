package com.fuyouj.service.solo.impl;

import com.framework.core.annotation.Service;
import com.fuyouj.entity.bo.ShopCategory;
import com.fuyouj.entity.dto.Result;
import com.fuyouj.service.solo.ShopCategoryService;

import java.util.List;

/**
 * @author FuYouJ
 * @date 2020/4/23  15:26
 **/
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Override
    public Result<Boolean> addShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> queryShopCategoryById(int shopCategoryId) {
        return null;
    }


    @Override
    public Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
        return null;
    }
}
