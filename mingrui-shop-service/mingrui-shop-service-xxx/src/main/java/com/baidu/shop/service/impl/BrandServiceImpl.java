package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.entity.CategoryBrandEntity;
import com.baidu.shop.mapper.BrandMapper;
import com.baidu.shop.mapper.CategoryBrandMapper;
import com.baidu.shop.service.BrandService;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.baidu.shop.utils.ObjectUtil;
import com.baidu.shop.utils.PinYinUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BrandServiceImpl
 * @Description: TODO
 * @Author lss
 * @Date 2020/12/25
 * @Version V1.0
 **/
@RestController
public class BrandServiceImpl extends BaseApiService implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO) {
        PageHelper.startPage(brandDTO.getPage(),brandDTO.getRows());

        if(!StringUtils.isEmpty(brandDTO.getSort())) PageHelper.orderBy(brandDTO.getOrderBy());

        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO,BrandEntity.class);

        Example example = new Example(BrandEntity.class);
        if (ObjectUtil.isNotNull(brandEntity.getName())){
            example.createCriteria().andLike("name","%"+brandEntity.getName()+"%");
        }


        List<BrandEntity>brandEntities=brandMapper.selectByExample(example);

        PageInfo<BrandEntity>pageInfo = new PageInfo<>(brandEntities);

        return this.setResultSuccess(pageInfo);
    }
    //新增
    @Transactional
    @Override
    public Result<JSONObject> saveBrand(BrandDTO brandDTO) {
        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);
        brandEntity.setLetter(PinYinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]),false).toCharArray()[0]);
        brandMapper.insertSelective(brandEntity);

        this.saveOrUpdate(brandDTO.getCategories(),brandEntity.getId());
        return this.setResultSuccess();
    }
    //修改
    @Transactional
    @Override
    public Result<JSONObject> editBrand(BrandDTO brandDTO) {
        BrandEntity brandEntity = BaiduBeanUtil.copyProperties(brandDTO, BrandEntity.class);
        brandEntity.setLetter(PinYinUtil.getUpperCase(String.valueOf(brandEntity.getName().toCharArray()[0]),false).toCharArray()[0]);
        brandMapper.updateByPrimaryKeySelective(brandEntity);
        //通过brandId删除中间表
        this.deleteCategoryBrandByBrandId(brandDTO.getId());
        //批量新增  新增
        this.saveOrUpdate(brandDTO.getCategories(),brandEntity.getId());
        return this.setResultSuccess();
    }
    @Transactional
    @Override
    public Result<JSONObject> deleteBrand(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
        this.deleteCategoryBrandByBrandId(id);
        return this.setResultSuccess();
    }

    @Override
    public Result<List<BrandEntity>> getBrandInfoByCategoryId(Integer cid) {
        List<BrandEntity> brandInfoByCategoryId = brandMapper.getBrandInfoByCategoryId(cid);
        return this.setResultSuccess(brandInfoByCategoryId);
    }

    //批量新增 新增
    private void saveOrUpdate(String categories,Integer brandId){
        if(StringUtils.isEmpty(categories)) throw new RuntimeException("分类信息不能为空");

        //判断分类集合字符串中是否包含,
        if(categories.contains(",")){//多个分类 --> 批量新增

            categoryBrandMapper.insertList(
                    Arrays.asList(categories.split(","))
                            .stream()
                            .map(categoryIdStr -> new CategoryBrandEntity(Integer.valueOf(categoryIdStr)
                                    ,brandId))
                            .collect(Collectors.toList())
            );

        }else{//普通单个新增

            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
            categoryBrandEntity.setBrandId(brandId);
            categoryBrandEntity.setCategoryId(Integer.valueOf(categories));

            categoryBrandMapper.insertSelective(categoryBrandEntity);
        }
//        String categories = brandDTO.getCategories();
//        if(StringUtils.isEmpty(brandDTO.getCategories())) return this.setResultError("");
//        List<CategoryBrandEntity> categoryBrandEntities  = new ArrayList<>();
//
//        if(categories.contains(",")){
//            String[] categoryArr  = categories.split(",");
//
//            /*for (String s : categoryArr){
//                CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
//                categoryBrandEntity.setBrandId(brandEntity.getId());
//                categoryBrandEntity.setCategoryId(Integer.valueOf(s));
//                categoryBrandEntities.add(categoryBrandEntity);
//            }
//                categoryBrandMapper.insertList(categoryBrandEntities);*/
//            categoryBrandMapper.insertList(Arrays.asList(categoryArr).stream().map(categoryIdStr -> {
//                CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
//                categoryBrandEntity.setBrandId(brandEntity.getId());
//                categoryBrandEntity.setCategoryId(Integer.valueOf(categoryIdStr));
//                return categoryBrandEntity;
//            }).collect(Collectors.toList()));
//
//        }else{
//            CategoryBrandEntity categoryBrandEntity = new CategoryBrandEntity();
//            categoryBrandEntity.setBrandId(brandEntity.getId());
//            categoryBrandEntity.setCategoryId(Integer.valueOf(categories));
//            categoryBrandMapper.insertSelective(categoryBrandEntity);
//        }
    }
    //通过brandId删除中间表
    private void deleteCategoryBrandByBrandId(Integer brandId){
        Example example = new Example(CategoryBrandEntity.class);
        example.createCriteria().andEqualTo("brandId",brandId);
        categoryBrandMapper.deleteByExample(example);
    }
}
