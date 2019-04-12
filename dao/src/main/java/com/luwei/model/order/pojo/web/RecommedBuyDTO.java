package com.luwei.model.order.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecommedBuyDTO {

    @ApiModelProperty(value = "课程ID" )//可拿到课程名称,简介,价格
    @NotNull(message = "课程ID不能为空" )
    private Integer serviceId;

    @ApiModelProperty(value = "课程ID" )//可拿到课程名称,简介,价格
    private Integer parentId;

}
