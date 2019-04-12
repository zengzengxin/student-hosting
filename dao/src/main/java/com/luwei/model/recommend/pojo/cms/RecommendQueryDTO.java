package com.luwei.model.recommend.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecommendQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "学校ID不能为空" )
    @ApiModelProperty(value = "学校ID" )
    private Integer schoolId;

}
