package com.luwei.model.course.pojo.mini;

import com.alibaba.fastjson.annotation.JSONField;
import com.luwei.common.config.ToTimeStampSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class MyCourseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程套餐ID")
    private Integer coursePackageId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课室")
    private String classroom;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "上课时间")
    private LocalDateTime classTime;

    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    @ApiModelProperty(value = "下课时间")
    private LocalDateTime quittingTime;

    // TODO 已报名人数
    @ApiModelProperty(value = "最大人数")
    private Integer maxNumber;

}
