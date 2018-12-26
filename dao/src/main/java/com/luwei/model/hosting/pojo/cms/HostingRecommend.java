package com.luwei.model.hosting.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingRecommend implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "托管ID不能为空")
    @ApiModelProperty(value = "托管ID")
    private Integer hostingId;

    @NotNull(message = "是否设为推荐不能为空")
    @ApiModelProperty(value = "是否设为推荐(true/false)")
    private Boolean recommend;

}