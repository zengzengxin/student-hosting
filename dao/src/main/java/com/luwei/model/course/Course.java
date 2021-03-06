package com.luwei.model.course;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_course" )
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID" )
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;


    private BigDecimal price;

    @ApiModelProperty(value = "课程名称" )
    private String courseName;

    @ApiModelProperty(value = "课程封面" )
    private String coverUrl;

    @ApiModelProperty(value = "课程简介" )
    private String introduction;

    @ApiModelProperty(value = "课程详情" )
    private String details;

    @ApiModelProperty(value = "教师ID" )
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称" )
    private String teacherName;

    @ApiModelProperty(value = "所在学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @ApiModelProperty(value = "上架到公众号" )
    private Boolean display;

    @ApiModelProperty(value = "是否设为推荐" )
    private Boolean recommend;

    @ApiModelProperty(value = "创建时间" )
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间" )
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除" )
    @TableLogic
    private Boolean deleted;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
