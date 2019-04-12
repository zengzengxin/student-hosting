package com.luwei.model.order.pojo.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class OrderQueryDto {

    @ApiModelProperty(value = "家长ID" )
    @TableId(value = "parent_id", type = IdType.AUTO)
    private Integer parentId;
}
