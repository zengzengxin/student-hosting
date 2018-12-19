package com.luwei.model.child.pojo.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ffq
 * @since 2018-12-11
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的姓名")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "孩子的性别，0为男，1为女，默认为0")
    @NotNull
    private Boolean gender;

    @ApiModelProperty(value = "（孩子）学生的学号")
    @NotBlank
    private String studentNo;

    @ApiModelProperty(value = "孩子的生日")
    @NotNull
    private LocalDateTime birthday;

    @ApiModelProperty(value = "学校的id")
    @NotNull
    private Integer schoolId;

    @ApiModelProperty(value = "孩子所在的学校")
    @NotBlank
    private String schoolName;

    @ApiModelProperty(value = "孩子的年级")
    @NotBlank
    private String grade;

    @ApiModelProperty(value = "孩子的班级")
    @NotBlank
    private String childClass;

    @ApiModelProperty(value = "孩子的班主任的电话")
    @NotBlank
    private String headteacherPhone;

    @ApiModelProperty(value = "孩子的班主任的姓名")
    @NotBlank
    private String headteacherName;

}
