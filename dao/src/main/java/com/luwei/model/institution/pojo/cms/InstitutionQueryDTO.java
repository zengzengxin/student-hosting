package com.luwei.model.institution.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2019-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InstitutionQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "负责人电话")
    private String leaderPhone;

}
