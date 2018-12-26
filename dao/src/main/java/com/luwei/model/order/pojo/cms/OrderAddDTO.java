package com.luwei.model.order.pojo.cms;

import com.luwei.model.order.envm.OrderStatusEnum;
import com.luwei.model.order.envm.OrderTypeEnum;
import com.luwei.model.order.envm.PaymentEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class OrderAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @ApiModelProperty(value = "下单用户(ID)")
    @NotNull(message = "下单用户(ID)不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "下单用户联系方式")
    @NotBlank(message = "下单用户联系方式不能为空")
    private String parentPhone;

    @ApiModelProperty(value = "孩子id")
    @NotNull(message = "孩子id不能为空")
    private Integer childId;

    @ApiModelProperty(value = "子女姓名")
    @NotBlank(message = "子女姓名不能为空")
    private String childName;

    @ApiModelProperty(value = "子女学号")
    @NotBlank(message = "子女学号不能为空")
    private String childStudentNo;

    @ApiModelProperty(value = "孩子的班级")
    @NotBlank(message = "孩子的班级不能为空")
    private String childClass;

    @ApiModelProperty(value = "支付方式 0-微信支付 1-支付宝支付")
    @NotNull(message = "支付方式0-微信支付 1-支付宝支付不能为空")
    private PaymentEnum payment;

    @ApiModelProperty(value = "课程ID")
    @NotNull(message = "不能为空")
    private Integer serviceId;

    @ApiModelProperty(value = "课程名称")
    @NotBlank(message = "课程名称不能为空")
    private String serviceName;

    @ApiModelProperty(value = "课程简介")
    @NotBlank(message = "课程简介不能为空")
    private String introduction;

    @ApiModelProperty(value = "价格")
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "课程开始时间")
    @NotNull(message = "课程开始时间不能为空")
    private LocalDateTime serviceStartTime;

    @ApiModelProperty(value = "课程结束时间")
    @NotNull(message = "课程结束时间不能为空")
    private LocalDateTime serviceEndTime;

    @ApiModelProperty(value = "学校名称")
    @NotBlank(message = "学校名称不能为空")
    private String schoolName;

    @ApiModelProperty(value = "支付时间")
    @NotNull(message = "支付时间不能为空")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品")
    @NotNull(message = "订单类型 0-课程 1-托管 2-餐品不能为空")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效")
    @NotNull(message = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效不能为空")
    private OrderStatusEnum orderStatus;

}
