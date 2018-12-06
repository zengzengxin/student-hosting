package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ManagerEditPasswordVO {

    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Integer managerId;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
