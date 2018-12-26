package com.luwei.model.teacher.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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

    @ApiModelProperty(value = "教师ID")
    @TableId(value = "teacher_id", type = IdType.AUTO)
    @NotNull(message = "teacherId不能为空")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    @NotBlank(message = "教师名称不能为空")
    private String teacherName;

    @ApiModelProperty(value = "老师电话")
    @NotBlank(message = "老师电话不能为空")
    private String phone;

    @ApiModelProperty(value = "老师资质执照")
    @NotBlank(message = "老师资质执照不能为空")
    private String license;

    @ApiModelProperty(value = "老师所在学校")
    @NotBlank(message = "老师所在学校不能为空")
    private String schoolName;

    @ApiModelProperty(value = "任课年级")
    @NotBlank(message = "任课年级不能为空")
    private String grade;

    @ApiModelProperty(value = "所在班级")
    @NotBlank(message = "所在班级不能为空")
    private String teacherClass;




}
