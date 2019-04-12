package com.luwei.model.search;

import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author huanglp
 * Date: 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchWebVO {

    @ApiModelProperty(value = "课程/托管id" )
    private Integer serviceId;

    @ApiModelProperty(value = "课程/托管名称" )
    private String serviceName;

    @ApiModelProperty(value = "类型 0课程 1托管" )
    private ServiceTypeEnum serviceType;
}
