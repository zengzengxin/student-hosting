package com.luwei.model.institution.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2019-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstitutionAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "机构名称不能为空")
    @ApiModelProperty(value = "机构名称")
    private String name;

    @NotNull(message = "机构介绍不能为空")
    @ApiModelProperty(value = "机构介绍")
    private String introduction;

    @NotNull(message = "负责人姓名不能为空")
    @ApiModelProperty(value = "负责人姓名")
    private String leaderName;

    @NotNull(message = "负责人电话不能为空")
    @ApiModelProperty(value = "负责人电话")
    private String leaderPhone;

    @NotNull(message = "机构执照不能为空")
    @ApiModelProperty(value = "机构执照")
    private String license;

}
