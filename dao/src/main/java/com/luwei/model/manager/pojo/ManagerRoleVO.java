package com.luwei.model.manager.pojo;

import com.luwei.common.constant.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerRoleVO {

    @ApiModelProperty("主键id" )
    @NotNull(message = "id不能为空" )
    private Integer managerId;

    @ApiModelProperty("角色" )
    @NotNull(message = "角色不能为空" )
    private RoleEnum role;

}
