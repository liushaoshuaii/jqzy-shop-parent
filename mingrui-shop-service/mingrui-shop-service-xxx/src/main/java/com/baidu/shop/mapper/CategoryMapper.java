package com.baidu.shop.mapper;

import com.baidu.shop.entity.CategoryEntity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<CategoryEntity>, SelectByIdListMapper<CategoryEntity,Integer> {
    @Select(value = "select c.id,c.name from tb_category c where c.id in (select category_id from tb_category_brand t where t.brand_id=#{brandId})")
    List<CategoryEntity> getCategoryByBrandId(Integer brandId);
}

