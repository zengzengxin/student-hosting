package com.luwei.models.user.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author jdq
 * @date 2017/12/15 14:14
 * UV
 */
@Entity
@Table(name = "tb_user_view", indexes = {@Index(columnList = "userId,protoId", unique = true)})
@Data
public class UserView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("协议id")
    private Integer protoId;

    @ApiModelProperty("创建时间")
    private Date createTime = new Date();

}
