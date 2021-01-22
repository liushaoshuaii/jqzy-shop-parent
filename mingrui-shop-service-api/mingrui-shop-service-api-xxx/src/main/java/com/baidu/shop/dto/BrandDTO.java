package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName BrandDTO
 * @Description: TODO
 * @Author lss
 * @Date 2021/1/22
 * @Version V1.0
 **/
@Data
@ApiModel(value="品牌dto")
public class BrandDTO extends BaseDTO {
    @ApiModelProperty(value="品牌主键",example = "1")
    @NotNull(message = "主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;
    @ApiModelProperty(value = "品牌名字")
    @NotNull(message = "品牌名字不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;
    @ApiModelProperty(value="品牌图片")
    private String image;
    @ApiModelProperty(value="品牌首字母")
    private Character letter;
}
