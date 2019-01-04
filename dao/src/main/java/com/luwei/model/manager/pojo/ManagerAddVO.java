package com.luwei.model.manager.pojo;

import com.luwei.common.constant.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @ApiModelProperty(value = "角色 0-平台管理员 1-教育局管理员 2-学校/机构管理员")
    @NotNull(message = "角色不能为空")
    private RoleEnum role;

    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

}
