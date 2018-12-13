package com.luwei.model.order.pojo.web;

import com.luwei.model.order.envm.OrderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConfirmOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "下单用户(ID)")
    private Integer parentId;

    @ApiModelProperty(value = "下单用户联系方式")
    private String parentPhone;

    @ApiModelProperty(value = "孩子id")
    private Integer childId;

    @ApiModelProperty(value = "子女姓名")
    private String childName;

    @ApiModelProperty(value = "子女学号")
    private Integer childStudentNo;

    @ApiModelProperty(value = "课程ID")//可拿到课程名称,简介,价格
    private Integer serviceId;

    @ApiModelProperty(value = "课程套餐ID")
    private Integer packageId;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品")
    private OrderTypeEnum orderType;

}