package com.luwei.model.child.pojo.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class ChildWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的主键id")
    private Integer childId;

    @ApiModelProperty(value = "孩子的姓名")
    private String name;

    @ApiModelProperty(value = "孩子的性别，0为不确定，1为男，2为女 默认为0")
    private Integer gender;

    @ApiModelProperty(value = "（孩子）学生的学号")
    private String studentNo;

    @ApiModelProperty(value = "孩子的生日")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "学校的id")
    private Integer schoolId;

    @ApiModelProperty(value = "孩子所在的学校")
    private String schoolName;

    @ApiModelProperty(value = "孩子的年级")
    private String grade;

    @ApiModelProperty(value = "孩子的班级")
    private String childClass;

    @ApiModelProperty(value = "孩子的班主任的电话")
    private String headteacherPhone;

    @ApiModelProperty(value = "孩子的班主任的姓名")
    private String headteacherName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
