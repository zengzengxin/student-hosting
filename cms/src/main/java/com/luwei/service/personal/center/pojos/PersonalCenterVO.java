package com.luwei.service.personal.center.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonalCenterVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("密码")
    private String password;

}
