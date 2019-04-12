package com.luwei.model.institution.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2019-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstitutionCmsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构ID" )
    private Integer institutionId;

    @ApiModelProperty(value = "机构名称" )
    private String name;

    @ApiModelProperty(value = "机构介绍" )
    private String introduction;

    @ApiModelProperty(value = "负责人姓名" )
    private String leaderName;

    @ApiModelProperty(value = "负责人电话" )
    private String leaderPhone;

    @ApiModelProperty(value = "机构执照" )
    private String license;

    @ApiModelProperty(value = "绑定学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "绑定学校名称" )
    private String schoolName;

}
