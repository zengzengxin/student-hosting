package com.luwei.model.order.pojo.cms;

import com.luwei.model.order.envm.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String serviceName;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效")
    private OrderStatusEnum orderStatus;

}
