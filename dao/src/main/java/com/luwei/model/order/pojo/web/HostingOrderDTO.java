package com.luwei.model.order.pojo.web;

import com.luwei.model.hosting.envm.HostingTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author huanglp
 * Date: 2018-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingOrderDTO {

    @ApiModelProperty(value = "孩子id")
    @NotNull(message = "孩子id不能为空")
    private Integer childId;

    @ApiModelProperty(value = "课程ID")//可拿到课程名称,简介,价格
    @NotNull(message = "课程ID不能为空")
    private Integer serviceId;

    @ApiModelProperty(value = "托管开始时间", dataType = "java.lang.Long")
    @NotNull(message = "托管开始时间不能为空")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "托管结束时间", dataType = "java.lang.Long")
    @NotNull(message = "托管结束时间不能为空")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管类型 0全托 1午托 2晚托")
    @NotNull(message = "托管类型 0全托 1午托 2晚托不能为空")
    private HostingTypeEnum hostingType;
}
