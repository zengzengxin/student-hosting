package com.luwei.model.course.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseAddDTOS {



    @ApiModelProperty(value = "课程名称" )
    private String courseName;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @ApiModelProperty(value = "课程简介" )
    private String introduction;


    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管价格" )
    private BigDecimal price;

    @ApiModelProperty(value = "是否设为推荐" )
    private Boolean recommend;


    @ApiModelProperty(value = "课程封面" )
    private String coverUrl;

    @ApiModelProperty(value = "课程详情" )
    private String details;








}
