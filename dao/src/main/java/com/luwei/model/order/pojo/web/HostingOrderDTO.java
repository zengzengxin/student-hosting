package com.luwei.model.order.pojo.web;

import com.luwei.model.hosting.envm.HostingTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private Integer childId;


    @ApiModelProperty(value = "课程ID")//可拿到课程名称,简介,价格
    private Integer serviceId;

    @ApiModelProperty(value = "托管开始时间",dataType = "java.lang.Long")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "托管结束时间",dataType = "java.lang.Long")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管类型 0全托 1午托 2晚托")
    private HostingTypeEnum hostingType;
}
