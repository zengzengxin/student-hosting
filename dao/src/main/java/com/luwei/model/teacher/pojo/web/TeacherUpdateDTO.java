package com.luwei.model.teacher.pojo.web;

import com.luwei.model.school.envm.SchoolTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private Integer teacherId;

    @NotNull
    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @NotNull
    @ApiModelProperty(value = "老师电话")
    private String phone;

    @NotNull
    @ApiModelProperty(value = "学校类型")
    private SchoolTypeEnum schoolType;

    @NotNull
    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @NotNull
    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "任课年级")
    private String grade;

    @ApiModelProperty(value = "所在班级")
    private String teacherClass;

}
