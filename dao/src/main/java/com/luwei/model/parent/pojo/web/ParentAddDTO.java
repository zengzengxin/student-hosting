package com.luwei.model.parent.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentAddDTO {

    @NotBlank(message = "姓名不能为空")
    private String parentName;

    @NotBlank(message = "电话号码不能为空")
    private String phone;

    @NotBlank(message = "账号不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

}
