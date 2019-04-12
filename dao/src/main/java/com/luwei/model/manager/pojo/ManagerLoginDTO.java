package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ManagerLoginDTO {

    @ApiModelProperty(value = "管理员账户" )
    private String account;


    @ApiModelProperty(value = "密码" )
    private String password;
}
