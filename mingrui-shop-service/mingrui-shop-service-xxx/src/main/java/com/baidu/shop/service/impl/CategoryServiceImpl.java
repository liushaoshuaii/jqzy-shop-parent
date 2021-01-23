package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.mapper.CategoryMapper;
import com.baidu.shop.service.CategoryService;
import com.baidu.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author lss
 * @Date 2021/1/20
 * @Version V1.0
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);
        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    @Override
    public Result<JSONObject> delCategory(Integer id) {
        //检验id是否合法
        if(ObjectUtil.isNull(id) || id<=0) return this.setResultError("id不合法");
        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);
        //判断id是否存在
        if(ObjectUtil.isNull(categoryEntity))return this.setResultError("数据不存在");
        //判断当前节点是否为父节点(isParent为1)
        if(categoryEntity.getIsParent()==1)return this.setResultError("当前节点为父节点");


        //通过当前节点的父节点id查询当前节点的父节点下是否还有其他子节点;
        Example example = new Example(CategoryEntity.class);
        //前边格式固定 拼接sql语句   where 1 = 1; select form 表明 where 1=1 and parentId=?
        example.createCriteria().andEqualTo("parentId", categoryEntity.getParentId());
        //将拼接后的sql返回到list集合内
        List<CategoryEntity> categoryEntities = categoryMapper.selectByExample(example);



        //如果size<=1,如果当前节点被删除的话,当前节点父节点下就没有其他节点,将父节点的状态改为0
        if(categoryEntities.size()<=1){
            CategoryEntity updateCategoryEntity = new CategoryEntity();
            updateCategoryEntity.setIsParent(0);
            updateCategoryEntity.setId(categoryEntity.getParentId());

            categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);

        }
        //通过id删除节点
        categoryMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }
    @Transactional
    @Override
    public Result<JSONObject> updateCategory(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);

        return this.setResultSuccess();
    }
    @Transactional
    @Override
    public Result<JSONObject> saveCategory(CategoryEntity categoryEntity) {
        CategoryEntity categoryEntity1 = new CategoryEntity();
        //根据前台传过来的数据的ParentId 获得他父节点的ID
        categoryEntity1.setId(categoryEntity.getParentId());
        categoryEntity1.setIsParent(1);
        //把父节点的IsParent属性修改为1 (声明前台传过来的ID的父节点为父节点)
        categoryMapper.updateByPrimaryKeySelective(categoryEntity1);

        //新增
        categoryMapper.insertSelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Override
    public Result<List<CategoryEntity>> getByBrand(Integer brandId) {
        List<CategoryEntity> categoryByBrandId = categoryMapper.getCategoryByBrandId(brandId);
        return this.setResultSuccess(categoryByBrandId);
    }

}
