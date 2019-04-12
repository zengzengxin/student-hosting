package com.luwei.model.hosting.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author huanglp
 * Date: 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingDisplay {

    @NotNull(message = "托管ID不能为空" )
    @ApiModelProperty(value = "托管ID" )
    private Integer hostingId;

    @NotNull(message = "上架到公众号不能为空" )
    @ApiModelProperty(value = "上架到公众号" )
    private Boolean display;
}


