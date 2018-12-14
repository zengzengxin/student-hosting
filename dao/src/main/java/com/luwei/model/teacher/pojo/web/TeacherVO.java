package com.luwei.model.teacher.pojo.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-13
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师ID")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "老师电话")
    private String phone;

    @ApiModelProperty(value = "老师资质执照")
    private String license;

    @ApiModelProperty(value = "老师所在学校")
    private String school;

    @ApiModelProperty(value = "任课年级")
    private String grade;

    @ApiModelProperty(value = "所在班级")
    private String teacherClass;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "学校id")
    @NotNull
    private Integer schoolId;

}
