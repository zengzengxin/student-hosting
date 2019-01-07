package com.luwei.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @TableId(value = "order_id", type = IdType.INPUT)
    private String orderId;

    @ApiModelProperty(value = "下单用户(ID)")
    private Integer parentId;

    @ApiModelProperty(value = "下单用户的名称")
    private String parentName;

    @ApiModelProperty(value = "下单用户联系方式")
    private String parentPhone;

    @ApiModelProperty(value = "下单用户昵称")
    private String nickName;

    @ApiModelProperty(value = "孩子id")
    private Integer childId;

    @ApiModelProperty(value = "子女姓名")
    private String childName;

    @ApiModelProperty(value = "子女学号")
    private String childStudentNo;

    @ApiModelProperty(value = "孩子的年级")
    private String childGrade;

    @ApiModelProperty(value = "孩子的班级")
    private String childClass;

    @ApiModelProperty(value = "支付方式 0-微信支付 1-支付宝支付")
    private PaymentEnum payment;

    @ApiModelProperty(value = "课程/托管ID")
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

    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品")
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效")
    private OrderStatusEnum orderStatus;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除 ")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "01-06新增: 上课时间")
    private LocalDateTime classTime;

    @ApiModelProperty(value = "01-06新增: 下课时间")
    private LocalDateTime quittingTime;

    @ApiModelProperty(value = "01-06新增: 教师id")
    private Integer teacherId;

    @ApiModelProperty(value = "01-06新增: 教师名称")
    private String teacherName;

}
