package com.fuyouj.service.solo;

import com.fuyouj.entity.bo.ShopCategory;
import com.fuyouj.entity.dto.Result;

import java.util.List;

public interface ShopCategoryService {
    //添加
    Result<Boolean> addShopCategory(ShopCategory shopCategory);
    //删除
    Result<Boolean>  removeShopCategory(int shopCategoryId);
    //修改
    Result<Boolean>  modifyShopCategory(ShopCategory shopCategory);
    //查询
    Result<ShopCategory>  queryShopCategoryById(int shopCategoryId);
    //获取头条列表
    Result<List<ShopCategory>>  queryShopCategory(ShopCategory shopCategoryCondition, int pageIndex, int pageSize);
}
