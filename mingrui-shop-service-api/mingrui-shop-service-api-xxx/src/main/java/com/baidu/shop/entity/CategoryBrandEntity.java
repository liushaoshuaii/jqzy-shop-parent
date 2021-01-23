package com.baidu.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @ClassName CategoryBrandEntity
 * @Description: TODO
 * @Author lss
 * @Date 2021/1/23
 * @Version V1.0
 **/
@Table(name = "tb_category_brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBrandEntity {
    private Integer categoryId;

    private Integer brandId;
}
