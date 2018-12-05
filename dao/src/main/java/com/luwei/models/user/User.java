package com.luwei.models.user;

import com.luwei.common.utils.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author jdq
 * @date 2017/12/7 11:56
 * 用户
 */
@Entity
@Data
public class User extends IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty("用户id")
    @Column(insertable = false, updatable = false, columnDefinition = "INT UNSIGNED COMMENT '用户id'")
    private Integer userId;

    @ApiModelProperty("昵称")
    @Column(length = 50, columnDefinition = "VARCHAR(255) NOT NULL COMMENT '昵称'")
    private String nickname;

    @ApiModelProperty("是否已被禁用。false：未禁用(默认)，true：已禁用")
    @Column(columnDefinition = "BOOLEAN NOT NULL COMMENT '是否已被禁用。false：未禁用(默认)，true：已禁用'")
    private Boolean disabled = false;

}
