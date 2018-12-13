package com.luwei.model.school.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-13
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
public class SchoolQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校id")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String name;


    @ApiModelProperty(value = "负责人电话")
    private String leaderPhone;



}
