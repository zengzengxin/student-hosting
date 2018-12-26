package com.luwei.model.child.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ffq
 * @since 2018-12-11
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的主键id")
    private Integer childId;

    @ApiModelProperty(value = "孩子的姓名")
    private String name;

    @ApiModelProperty(value = "孩子的性别，0为男，1为女，默认为0")
    private Boolean gender;

    @ApiModelProperty(value = "（孩子）学生的学号")
    private String studentNo;

    @ApiModelProperty(value = "学校的id")
    private Integer schoolId;

    @ApiModelProperty(value = "孩子所在的学校")
    private String schoolName;

    @ApiModelProperty(value = "孩子的年级")
    private String grade;

    @ApiModelProperty(value = "孩子的班级")
    private String childClass;

}
