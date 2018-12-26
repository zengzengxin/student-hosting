package com.luwei.model.child.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "孩子的性别，0为男，1为女，默认为0")
    @NotNull(message = "性别不能为空")
    private Boolean gender;

    @ApiModelProperty(value = "（孩子）学生的学号")
    @NotBlank(message = "学号不能为空")
    private String studentNo;

    @ApiModelProperty(value = "孩子所在的学校")
    @NotBlank(message = "所在学校不能为空")
    private String schoolName;

    @ApiModelProperty(value = "孩子的年级")
    @NotBlank(message = "年级不能为空")
    private String grade;

    @ApiModelProperty(value = "孩子的班级")
    @NotBlank(message = "班级不能为空")
    private String childClass;


}
