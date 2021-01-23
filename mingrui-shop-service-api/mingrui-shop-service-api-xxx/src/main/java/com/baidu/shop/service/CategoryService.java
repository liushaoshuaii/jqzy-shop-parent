package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品分类接口")
public interface CategoryService {
    @ApiOperation("通过查询商品分类")
    @GetMapping({"category/list"})
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "通过ID删除商品分类")
    @DeleteMapping(value = "category/del")
    Result<JSONObject> delCategory(Integer id);

    @ApiOperation(value ="修改商品分类")
    @PutMapping(value="category/update")
    Result<JSONObject>updateCategory(@Validated({MingruiOperation.Update.class}) @RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "新增商品分类")
    @PostMapping(value = "category/save")
    Result<JSONObject>saveCategory(@Validated({MingruiOperation.Add.class}) @RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "通过品牌id查询商品分类")
    @GetMapping(value = "category/brand")
    Result<List<CategoryEntity>> getByBrand(Integer brandId);
}
