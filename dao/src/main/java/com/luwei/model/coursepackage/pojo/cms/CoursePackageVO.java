package com.luwei.model.coursepackage.pojo.cms;

import com.alibaba.fastjson.annotation.JSONField;
import com.luwei.common.config.ToTimeStampSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CoursePackageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程套餐ID")
    private Integer coursePackageId;

    @ApiModelProperty(value = "课程开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "课程结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数")
    private Integer maxNumber;

    @ApiModelProperty(value = "上架状态")
    private Boolean display;

    @ApiModelProperty(value = "是否过期")
    private Boolean overdue;

    @ApiModelProperty(value = "课室")
    private String classroom;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "上课时间", dataType = "java.lang.Long")
    private LocalDateTime classTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "下课时间", dataType = "java.lang.Long")
    private LocalDateTime quittingTime;


}
