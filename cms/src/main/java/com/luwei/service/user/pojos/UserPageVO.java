package com.luwei.service.user.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author jdq
 * @date 2017/12/13 09:59
 */
@Data
public class UserPageVO {

    @ApiModelProperty("主键")
    private Integer userId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别，0:未知，1：男，2：女")
    private Integer gender;

    @ApiModelProperty("创建日期")
    private Date createTime;

}
