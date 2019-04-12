package com.luwei.model.course.pojo.cms;

import com.luwei.model.coursepackage.pojo.cms.CoursePackageCmsVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseCmsVOS implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID" )
    private Integer courseId;

    @ApiModelProperty(value = "课程名称" )
    private String courseName;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;


    @ApiModelProperty(value = "是否设为推荐" )
    private Boolean recommend;

    @ApiModelProperty(value = "课程价格" )
    private BigDecimal price;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    @ApiModelProperty(value = "课程简介" )
    private String introduction;

    @ApiModelProperty(value = "课程详情" )
    private String details;


}
