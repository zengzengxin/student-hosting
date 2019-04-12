package com.luwei.model.parent.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParentLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号" )
    private String userName;

    @ApiModelProperty(value = "密码" )
    private String password;

}
