package com.luwei.model.banner.pojo.cms;

import com.luwei.model.banner.envm.BannerTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Author: huanglp
 * Date: 2018-12-07
 */
@Data
public class BannerQueryDTO {

    @NotNull
    @ApiModelProperty(value = "轮播图类型")
    private BannerTypeEnum bannerType;
    
}
