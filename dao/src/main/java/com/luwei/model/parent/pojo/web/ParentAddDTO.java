package com.luwei.model.parent.pojo.web;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ffq
 * @since 2018-12-12
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_parent")
public class ParentAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "家长姓名")
    @NotBlank
    private String parentName;

    @ApiModelProperty(value = "家长联系方式")
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "家庭关系")
    @NotBlank
    private String familyRelation;

    @ApiModelProperty(value = "家庭住址")
    @NotBlank
    private String address;

    @ApiModelProperty(value = "家长头像")
    @NotBlank
    private String avatarUrl;

    @ApiModelProperty(value = "家长性别,0代表男，1代表女,默认为0")
    @NotNull
    private Integer gender;

    @ApiModelProperty(value = "家长昵称")
    @NotBlank
    private String nickName;

    @ApiModelProperty(value = "微信用户openid")
    private String openid;





}
