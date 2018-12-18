package com.luwei.model.order.pojo.cms;

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
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "孩子ID")
    private Integer childId;

    @ApiModelProperty(value = "孩子姓名")
    private String childName;

    @ApiModelProperty(value = "孩子学号")
    private String childStudentNo;

    @ApiModelProperty(value = "孩子的年级")
    private String childGrade;

    @ApiModelProperty(value = "孩子的班级")
    private String childClass;

    @ApiModelProperty(value = "课程ID")
    private Integer serviceId;

    @ApiModelProperty(value = "课程名称")
    private String serviceName;

    @ApiModelProperty(value = "服务(课程)封面")
    private String serviceCover;

    @ApiModelProperty(value = "课程简介")
    private String introduction;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课程开始时间")
    private LocalDateTime serviceStartTime;

    @ApiModelProperty(value = "课程结束时间")
    private LocalDateTime serviceEndTime;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效")
    private OrderStatusEnum orderStatus;

}
