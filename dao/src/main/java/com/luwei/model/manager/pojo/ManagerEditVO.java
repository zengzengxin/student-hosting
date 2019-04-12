package com.luwei.model.manager.pojo;

import com.luwei.common.constant.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author jdq
 * @date 2017/12/9 17:46
 */
@Data
public class ManagerEditVO {

    @ApiModelProperty("主键id" )
    @NotNull(message = "id不能为空" )
    private Integer managerId;

    @ApiModelProperty("名称" )
    @NotBlank(message = "请输入账号" )
    @Length(max = 20, message = "账号最大长度限制为20" )
    private String account;

    @ApiModelProperty(value = "学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "角色 0-平台管理员 1-教育局管理员 2-学校/机构管理员" )
    private RoleEnum role;

}
