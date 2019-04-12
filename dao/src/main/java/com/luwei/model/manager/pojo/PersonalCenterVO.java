package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonalCenterVO {

    @ApiModelProperty("名称" )
    private String name;

    @ApiModelProperty("密码" )
    private String password;

}
