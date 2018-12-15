package com.luwei.model.school.pojo.web;

import com.luwei.model.school.envm.schoolTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-13
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SchoolAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校名称")
    @NotBlank(message = "学校名称不能为空")
    @Size(min = 1, max = 12, message = "学校名称长度必须在【{min}和{max}】之间")
    private String name;

    @ApiModelProperty(value = "学校介绍")
    @NotBlank(message = "学校介绍不能为空")
    @Size(min = 1, max = 12, message = "学校介绍长度必须在【{min}和{max}】之间")
    private String introduction;

    @ApiModelProperty(value = "学校编码")
    @NotBlank(message = "学校编码不能为空")
    @Size(min = 1, max = 12, message = "学校编码长度必须在【{min}和{max}】之间")
    private String code;

    @ApiModelProperty(value = "负责人电话")
    @NotBlank(message = "负责人电话不能为空")
    @Size(min = 1, max = 12, message = "负责人电话长度必须在【{min}和{max}】之间")
    private String leaderPhone;

    @ApiModelProperty(value = "学校执照")
    @NotBlank(message = "学校执照不能为空")
    @Size(min = 1, max = 12, message = "学校执照长度必须在【{min}和{max}】之间")
    private String license;

    @ApiModelProperty(value = "学生人数")
    @NotNull(message = "学生人数不能为空")
    @Range(min = 0, max = 20000, message = "学生人数范围必须在【{min},{max}】之间")
    private Integer studentNumber;

    @ApiModelProperty(value = "学校类型(0/1)")
    @NotNull(message = "学校类型(0/1)不能为空")
    @Range(min = 0, max = 20000, message = "学校类型(0/1)范围必须在【{min},{max}】之间")
    private schoolTypeEnum type;

    @ApiModelProperty(value = "权限(0/1)")
    @NotNull(message = "权限(0/1)不能为空")
    @Range(min = 0, max = 20000, message = "权限(0/1)范围必须在【{min},{max}】之间")
    private Integer permission;

}
