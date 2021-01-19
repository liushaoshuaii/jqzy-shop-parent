package com.baidu.shop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName CategoryEntity
 * @Description: TODO
 * @Author lss
 * @Date 2021/1/18
 * @Version V1.0
 **/
@ApiModel
@Data
@Table(name="tb_category")
public class CategoryEntity {
    @Id
    @ApiModelProperty(value="类目id",example = "1")

    private Integer id;

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "父类目id,顶级类目填0",example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是",example = "1")
    private Integer isParent;

    @ApiModelProperty(value="排序指数，越小越靠前",example = "1")
    private Integer sort;
}