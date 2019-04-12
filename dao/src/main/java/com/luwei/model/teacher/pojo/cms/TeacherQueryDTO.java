package com.luwei.model.teacher.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-13
 */
@ApiModel(value = "" )
@Data
@EqualsAndHashCode(callSuper = false)
public class TeacherQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称" )
    private String teacherName;

    @ApiModelProperty(value = "老师电话" )
    private String phone;


}
