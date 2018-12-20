package com.luwei.model.order.pojo.cms;

import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.envm.OrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
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

    // TODO 将此查询条件区分
    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效")
    private OrderStatusEnum orderStatus;

    @NotNull(message = "订单类型不能为空")
    @ApiModelProperty(value = "订单类型")
    private OrderTypeEnum orderType;

}
