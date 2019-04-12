package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * description
 * create by LW-mochengdong
 * 2018/7/24
 */
@Data
public class ManagerResetPasswordVO {

    @ApiModelProperty("主键id" )
    @NotNull(message = "id不能为空" )
    private Integer managerId;

    @ApiModelProperty("密码" )
    @NotBlank(message = "请输入密码" )
    private String password;

}
