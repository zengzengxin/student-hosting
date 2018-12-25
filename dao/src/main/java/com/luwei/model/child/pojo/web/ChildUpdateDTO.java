package com.luwei.model.child.pojo.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ffq
 * @since 2018-12-11
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
public class ChildUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的主键id")
    private Integer childId;

    @ApiModelProperty(value = "孩子的生日")
    @NotNull
    private LocalDateTime birthday;


    @ApiModelProperty(value = "孩子的班主任的电话")
    @NotBlank
    private String headteacherPhone;

    @ApiModelProperty(value = "孩子的班主任的姓名")
    @NotBlank
    private String headteacherName;



}
