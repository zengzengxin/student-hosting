package com.luwei.model.school.pojo.cms;

import com.luwei.model.school.envm.SchoolTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-13
 */
@ApiModel(value = "" )
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SchoolCmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校id" )
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称" )
    private String name;

    @ApiModelProperty(value = "学校介绍" )
    private String introduction;

    @ApiModelProperty(value = "学校编码" )
    private String code;

    @ApiModelProperty(value = "学校负责人姓名" )
    private String leaderName;

    @ApiModelProperty(value = "负责人电话" )
    private String leaderPhone;

    @ApiModelProperty(value = "学校执照" )
    private String license;

    @ApiModelProperty(value = "学生人数" )
    private Integer studentNumber;

    @ApiModelProperty(value = "学校类型(0/1)" )
    private SchoolTypeEnum schoolType;

    @ApiModelProperty(value = "权限(0/1)" )
    private Integer permission;


}
