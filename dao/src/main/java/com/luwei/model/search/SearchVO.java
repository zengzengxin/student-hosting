package com.luwei.model.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author huanglp
 * Date: 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchVO {

    @ApiModelProperty(value = "课程/托管id")
    private Integer severId;

    @ApiModelProperty(value = "课程/托管名称")
    private String severName;

    @ApiModelProperty(value = "类型 0课程 1托管")
    private Integer severType;
}
