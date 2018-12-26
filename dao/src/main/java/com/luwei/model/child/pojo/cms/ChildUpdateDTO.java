package com.luwei.model.child.pojo.cms;

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
 * @author ffq
 * @since 2018-12-11
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的主键id")
    @TableId(value = "child_id", type = IdType.AUTO)
    @NotNull(message = "childId不能为空")
    private Integer childId;

    @ApiModelProperty(value = "孩子的姓名")
    @NotBlank(message = "孩子的姓名不能为空")
    private String name;

    @ApiModelProperty(value = "孩子的性别，0为男，1为女，默认为0")
    @NotNull(message = "孩子的性别不能为空")
    private Boolean gender;

    @ApiModelProperty(value = "（孩子）学生的学号")
    @NotBlank(message = "学生的学号不能为空")
    private String studentNo;

    @ApiModelProperty(value = "孩子所在的学校")
    @NotBlank(message = "所在的学校不能为空")
    private String schoolName;

    @ApiModelProperty(value = "孩子的年级")
    @NotBlank(message = "年级不能为空")
    private String grade;

    @ApiModelProperty(value = "孩子的班级")
    @NotBlank(message = "班级不能为空")
    private String childClass;

}
