package com.luwei.model.recommend.pojo.web;

import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecommendWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务ID" )
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称" )
    private String serviceName;

    @ApiModelProperty(value = "服务价格" )
    private BigDecimal servicePrice;

    @ApiModelProperty(value = "服务简介" )
    private String serviceIntroduction;

    @ApiModelProperty(value = "服务图片地址" )
    private String serviceCoverUrl;

    @ApiModelProperty(value = "权重" )
    private Integer weight;

    @ApiModelProperty(value = "学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @ApiModelProperty(value = "课程开始时间" )
    private LocalDateTime startTime;

    @ApiModelProperty(value = "课程结束时间" )
    private LocalDateTime endTime;

    @ApiModelProperty(value = "服务类型" )
    private ServiceTypeEnum serviceType;

}
