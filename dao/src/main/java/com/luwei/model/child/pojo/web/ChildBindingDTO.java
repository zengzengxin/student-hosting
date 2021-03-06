package com.luwei.model.child.pojo.web;

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
 * @since 2018-12-11
 */
@ApiModel(value = "" )
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChildBindingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的姓名" )
    @NotBlank(message = "姓名不能为空" )
    private String name;

    @ApiModelProperty(value = "（孩子）学生的学号" )
    @NotBlank(message = "学号不能为空" )
    private String studentNo;

    @ApiModelProperty(value = "学校的id" )
    @NotNull(message = "学校的id不能为空" )
    private Integer schoolId;

}
