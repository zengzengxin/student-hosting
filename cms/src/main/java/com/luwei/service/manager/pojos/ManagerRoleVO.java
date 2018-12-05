package com.luwei.service.manager.pojos;

import com.luwei.common.constants.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerRoleVO {

    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Integer managerId;

    @ApiModelProperty("角色")
    @NotNull(message = "角色不能为空")
    private RoleEnum role;

}
