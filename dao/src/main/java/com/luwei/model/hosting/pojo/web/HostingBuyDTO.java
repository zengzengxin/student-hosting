package com.luwei.model.hosting.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = false)
public class HostingBuyDTO {

    @ApiModelProperty(value = "课程ID" )//可拿到课程名称,简介,价格
    @NotNull(message = "课程ID不能为空" )
    private Integer hostingId;

    @ApiModelProperty(value = "课程ID" )//可拿到课程名称,简介,价格
    private Integer parentId;
}
