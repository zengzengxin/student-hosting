package com.luwei.model.order.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String serviceName;

}
