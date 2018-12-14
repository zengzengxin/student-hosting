package com.luwei.model.teacher.pojo.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class TeacherUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称")
    @NotBlank(message = "教师名称不能为空")
    @Size(min = 1, max = 12, message = "教师名称长度必须在【{min}和{max}】之间")
    private String teacherName;

    @ApiModelProperty(value = "老师电话")
    @NotBlank(message = "老师电话不能为空")
    @Size(min = 1, max = 12, message = "老师电话长度必须在【{min}和{max}】之间")
    private String phone;

    @ApiModelProperty(value = "老师资质执照")
    @NotBlank(message = "老师资质执照不能为空")
    @Size(min = 1, max = 12, message = "老师资质执照长度必须在【{min}和{max}】之间")
    private String license;

    @ApiModelProperty(value = "老师所在学校")
    @NotBlank(message = "老师所在学校不能为空")
    @Size(min = 1, max = 12, message = "老师所在学校长度必须在【{min}和{max}】之间")
    private String school;

    @ApiModelProperty(value = "任课年级")
    @NotBlank(message = "任课年级不能为空")
    @Size(min = 1, max = 12, message = "任课年级长度必须在【{min}和{max}】之间")
    private String grade;

    @ApiModelProperty(value = "所在班级")
    @NotBlank(message = "所在班级不能为空")
    @Size(min = 1, max = 12, message = "所在班级长度必须在【{min}和{max}】之间")
    private String teacherClass;

    @ApiModelProperty(value = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "学校id")
    @NotNull
    private Integer schoolId;

}
