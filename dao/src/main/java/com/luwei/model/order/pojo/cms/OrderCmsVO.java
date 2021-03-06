package com.luwei.model.order.pojo.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.luwei.common.config.ToTimeStampSerializer;
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
public class OrderCmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号" )
    private String orderId;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @ApiModelProperty(value = "孩子ID" )
    private Integer childId;

    @ApiModelProperty(value = "孩子姓名" )
    private String childName;

    @ApiModelProperty(value = "孩子学号" )
    private String childStudentNo;

    @ApiModelProperty(value = "孩子的年级" )
    private String childGrade;

    @ApiModelProperty(value = "孩子的班级" )
    private String childClass;

    @ApiModelProperty(value = "课程ID" )
    private Integer serviceId;

    @ApiModelProperty(value = "课程名称" )
    private String serviceName;

    @ApiModelProperty(value = "服务(课程)封面" )
    private String serviceCover;

    @ApiModelProperty(value = "课程简介" )
    private String introduction;

    @ApiModelProperty(value = "价格" )
    private BigDecimal price;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "课程开始时间" )
    private LocalDateTime serviceStartTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "课程结束时间" )
    private LocalDateTime serviceEndTime;

    @ApiModelProperty(value = "订单类型 0-课程 1-托管 2-餐品" )
    private OrderTypeEnum orderType;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已完成 3-过期失效" )
    private OrderStatusEnum orderStatus;

    @ApiModelProperty(value = "下单用户联系方式" )
    private String parentPhone;

    @ApiModelProperty(value = "下单用户名称" )
    private String parentName;

    @ApiModelProperty(value = "下单用户昵称" )
    private String nickName;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "下单时间" )
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付方式 0-微信支付 1-支付宝支付" )
    private PaymentEnum payment;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "支付时间" )
    private LocalDateTime payTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "01-06新增: 上课时间" )
    private LocalDateTime classTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "01-06新增: 下课时间" )
    private LocalDateTime quittingTime;

    @ApiModelProperty(value = "01-06新增: 教师id" )
    private Integer teacherId;

    @ApiModelProperty(value = "01-06新增: 教师名称" )
    private String teacherName;

}
