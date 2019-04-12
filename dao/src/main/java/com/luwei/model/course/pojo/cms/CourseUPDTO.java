package com.luwei.model.course.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseUPDTO {

    @ApiModelProperty(value = "课程ID" )
    @NotNull(message = "课程ID不能为空" )
    private Integer courseId;


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
