package com.luwei.model.order.pojo.web;

import com.luwei.model.order.envm.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MyOrderQueryDTO {

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效" )
    private OrderStatusEnum orderStatus;

}
