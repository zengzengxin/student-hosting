package com.luwei.service.manager.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jdq
 * @date 2017/12/9 17:30
 */
@Data
@AllArgsConstructor
public class LoginSuccessVO {

    @ApiModelProperty("role。admin：普通管理员，root：超级管理员")
    private Enum role;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("名称")
    private String name;
}
