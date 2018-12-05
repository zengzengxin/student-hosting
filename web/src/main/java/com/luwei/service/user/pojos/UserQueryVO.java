package com.luwei.service.user.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description
 * create by LW-mochengdong
 * 2018/7/24
 */
@Data
public class UserQueryVO {

    @ApiModelProperty("昵称")
    private String nickname;

}
