package com.luwei.model.manager;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.common.constant.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员ID")
    @TableId(value = "manager_id", type = IdType.AUTO)
    private Integer managerId;

    @ApiModelProperty(value = "管理员账户")
    private String account;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色 0-平台管理员 1-教育局管理员 2-学校/机构管理员")
    private RoleEnum role;

    @ApiModelProperty(value = "是否禁用 0-否 1-是")
    private Boolean disabled;

    @ApiModelProperty(value = "绑定学校ID")
    private Integer schoolId;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除状态true:已删除,false:未删除")
    @TableLogic
    private Boolean deleted;

}
