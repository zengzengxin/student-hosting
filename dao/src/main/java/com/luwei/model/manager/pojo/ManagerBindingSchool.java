package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Author: huanglp
 * Date: 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManagerBindingSchool {

    @NotNull(message = "管理员ID不能为空" )
    @ApiModelProperty(value = "管理员ID" )
    private Integer managerId;

    @NotNull(message = "学校ID不能为空" )
    @ApiModelProperty(value = "学校ID" )
    private Integer schoolId;
}
