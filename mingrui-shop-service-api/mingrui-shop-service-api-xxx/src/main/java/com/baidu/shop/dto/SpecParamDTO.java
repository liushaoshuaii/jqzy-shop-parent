package com.baidu.shop.dto;

import com.baidu.shop.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName SpecParamDTO
 * @Description: TODO
 * @Author lss
 * @Date 2021/1/26
 * @Version V1.0
 **/
@ApiModel("规格参数DTO")
@Data
public class SpecParamDTO {
    @ApiModelProperty(value = "主键",example = "1")
    @NotNull(message = "主键不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "分类Id",example = "1")
    @NotNull(message = "分类Id不能为空",groups = {MingruiOperation.Update.class})
    private Integer cid;

    @ApiModelProperty(value = "规格组Id",example = "1")
    @NotNull(message = "规格组Id不能为空",groups = {MingruiOperation.Update.class})
    private Integer groupId;

    @ApiModelProperty(value = "规格参数名称")
    @NotEmpty(message = "规格参数名称不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "是否是数字类型参数")
    @NotNull(message = "是否是数字类型参数不能为空s",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean numeric;

    @ApiModelProperty(value = "数字类型参数的单位")
    @NotEmpty(message = "数字类型参数的单位不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String unit;

    @ApiModelProperty(value = "是否是sku通用属性")
    @NotNull(message = "是否是sku通用属性不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean generic;

    @ApiModelProperty(value = "是否用于搜索过滤")
    @NotNull(message = "是否用于搜索过滤s不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private Boolean searching;

    @ApiModelProperty(value = "数值类型参数")
    @NotEmpty(message = "数值类型参数不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String segments;
}
