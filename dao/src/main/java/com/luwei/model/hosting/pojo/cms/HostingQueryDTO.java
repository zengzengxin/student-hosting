package com.luwei.model.hosting.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zzx
 * @since 2018-12-17
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
public class HostingQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "托管名称")
    private String name;




}
