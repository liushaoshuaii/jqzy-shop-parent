package com.baidu.shop.entity;
import com.baidu.shop.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "类目id不能为空",groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "类目名称")
    @NotNull(message = "类目名称不能为空",groups = {MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "父类目id,顶级类目填0",example = "1")
    @NotNull(message = "父类目id不能为空",groups = {MingruiOperation.Add.class})
    private Integer parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是",example = "1")
    @NotNull(message = "是否为父节点不能为空",groups = {MingruiOperation.Add.class})
    private Integer isParent;

    @ApiModelProperty(value="排序指数，越小越靠前",example = "1")
    @NotNull(message = "排序指数不能为空",groups = {MingruiOperation.Add.class})
    private Integer sort;
}
