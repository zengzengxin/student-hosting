package com.luwei.model.search;

import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: huanglp
 * Date: 2018-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchCmsVO {

    @ApiModelProperty(value = "课程/托管id")
    private Integer serviceId;

    @ApiModelProperty(value = "课程/托管名称")
    private String serviceName;

    @ApiModelProperty(value = "服务类型 0-课程 1-托管")
    private ServiceTypeEnum serviceType;

}
