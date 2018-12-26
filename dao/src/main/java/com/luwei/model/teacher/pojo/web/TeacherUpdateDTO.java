package com.luwei.model.teacher.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "教师ID")
    @NotNull(message = "教师id不能为空")
    private Integer teacherId;
    
    @ApiModelProperty(value = "任课年级")
    @NotBlank(message = "任课年级不能为空")
    private String grade;

    @ApiModelProperty(value = "所在班级")
    @NotBlank(message = "所在班级不能为空")
    private String teacherClass;

}
