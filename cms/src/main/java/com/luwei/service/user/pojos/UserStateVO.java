package com.luwei.service.user.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * description
 * create by LW-mochengdong
 * 2018/7/24
 */
@Data()
@EqualsAndHashCode(callSuper = false)
public class UserStateVO {

    @ApiModelProperty("主键id")
    @NotNull(message = "id不能为空")
    private Integer userId;
}
