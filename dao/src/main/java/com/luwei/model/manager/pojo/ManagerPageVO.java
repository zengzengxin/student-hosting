package com.luwei.model.manager.pojo;

import com.luwei.common.constants.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jdq
 * @date 2017/12/7 15:09
 */
@Data
public class ManagerPageVO {

    @ApiModelProperty("主键id")
    private Integer managerId;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("admin：普通管理员，root：超级管理员,operator 运营人员")
    private RoleEnum role;

    @ApiModelProperty("是否已被禁用。false：未禁用(默认)，true：已禁用")
    private Boolean disabled;

}
