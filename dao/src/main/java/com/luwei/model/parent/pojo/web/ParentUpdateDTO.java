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
public class ParentUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家长姓名")
    @NotBlank(message = "家长姓名不能为空")
    private String parentName;

    @ApiModelProperty(value = "家长联系方式")
    @NotBlank(message = "家长联系方式不能为空")
    private String phone;

    @ApiModelProperty(value = "家庭关系")
    @NotBlank(message = "家庭关系不能为空")
    private String familyRelation;

    @ApiModelProperty(value = "家庭住址")
    @NotBlank(message = "家庭住址不能为空")
    private String address;

    @ApiModelProperty(value = "家长头像")
    @NotBlank(message = "家长头像不能为空")
    private String avatarUrl;

}
