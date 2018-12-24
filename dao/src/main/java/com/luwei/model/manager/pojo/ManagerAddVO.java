package com.luwei.model.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author jdq
 * @date 2017/12/7 15:31
 */
@Data
public class ManagerAddVO {

    @ApiModelProperty("账户")
    @NotBlank(message = "请输入账户")
    @Length(max = 20, message = "账户最大长度限制为20")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "请输入密码")
    private String password;

}
