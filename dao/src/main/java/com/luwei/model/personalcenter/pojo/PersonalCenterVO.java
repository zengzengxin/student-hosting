package com.luwei.model.personalcenter.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonalCenterVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("密码")
    private String password;

}
