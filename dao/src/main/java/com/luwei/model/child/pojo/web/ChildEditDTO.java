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
 * @author zzx
 * @since 2018-12-11
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
public class ChildEditDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "孩子的主键id")
    @NotNull(message = "Id不能为空")
    private Integer childId;

    @ApiModelProperty(value = "孩子的生日")
    @NotNull(message = "生日不能为空")
    private LocalDateTime birthday;


    @ApiModelProperty(value = "孩子的班主任的电话")
    @NotBlank(message = "班主任的电话不能为空")
    private String headteacherPhone;

    @ApiModelProperty(value = "孩子的班主任的姓名")
    @NotBlank(message = "班主任的姓名不能为空")
    private String headteacherName;



}
