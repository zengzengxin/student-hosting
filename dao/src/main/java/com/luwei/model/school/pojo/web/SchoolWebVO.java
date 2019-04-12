package com.luwei.model.school.pojo.web;

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
public class SchoolWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校id" )
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称" )
    private String name;

}
