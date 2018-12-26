package com.luwei.model.order.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayForOrderDTO {

    @ApiModelProperty(value = "订单编号(ID)")
    @NotBlank(message = "订单编号(ID)不能为空")
    private String orderId;

}
