package com.luwei.model.manager;

import com.luwei.common.constants.RoleEnum;
import com.luwei.common.util.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author jdq
 * @date 2017/12/7 11:50
 * 管理员
 */
@Entity
@Data
public class Manager extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("管理员id")
    @Column(insertable = false, updatable = false, columnDefinition = "INT UNSIGNED COMMENT '管理员id'")
    private Integer managerId;

    @ApiModelProperty("账户")
    @Column(length = 20, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '账户'")
    private String account;

    @ApiModelProperty("用户名")
    @Column(length = 50, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '用户名'")
    private String name;

    @ApiModelProperty("密码")
    @Column(length = 32, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '密码'")
    private String password;

    @ApiModelProperty("角色。0：超级管理员，1：普通管理员")
    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '角色。0：超级管理员，1：普通管理员'")
    private RoleEnum role;

    @ApiModelProperty("是否已被禁用。false：未禁用(默认)，true：已禁用")
    @Column(columnDefinition = "BOOLEAN NOT NULL COMMENT '是否已被禁用。false：未禁用(默认)，true：已禁用'")
    private Boolean disabled = false;

}
