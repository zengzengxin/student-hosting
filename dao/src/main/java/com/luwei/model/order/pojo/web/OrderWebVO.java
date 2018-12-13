package com.luwei.model.order.pojo.web;

import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.envm.OrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号(ID)")
    private Long orderId;

    @ApiModelProperty(value = "课程ID")
    private Integer courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程简介")
    private String introduction;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课程开始时间")
    private LocalDateTime courseStartTime;

    @ApiModelProperty(value = "课程结束时间")
    private LocalDateTime courseEndTime;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效")
    private OrderStatusEnum orderStatus;

}

