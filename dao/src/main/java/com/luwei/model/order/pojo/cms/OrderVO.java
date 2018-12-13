package com.luwei.model.order.pojo.cms;

import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.envm.OrderTypeEnum;
import com.luwei.model.order.envm.PaymentEnum;
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
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

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

    @ApiModelProperty(value = "支付方式 0-微信支付 1-支付宝支付")
    private PaymentEnum payment;

    @ApiModelProperty(value = "课程ID")
    private Integer courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课程开始时间")
    private LocalDateTime courseStartTime;

    @ApiModelProperty(value = "课程结束时间")
    private LocalDateTime courseEndTime;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成")
    private OrderStatusEnum orderStatus;

}
