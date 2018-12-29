package com.luwei.model.manager.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.luwei.common.config.ToTimeStampSerializer;
import com.luwei.common.constant.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

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

    @ApiModelProperty("学校ID")
    private Integer schoolId;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("admin：普通管理员，root：超级管理员,operator 运营人员")
    private RoleEnum role;

    @ApiModelProperty("是否已被禁用。false：未禁用(默认)，true：已禁用")
    private Boolean disabled;

    @ApiModelProperty(value = "最后登录时间")
    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    @JSONField(serializeUsing = ToTimeStampSerializer.class)
    private LocalDateTime createTime;
}
