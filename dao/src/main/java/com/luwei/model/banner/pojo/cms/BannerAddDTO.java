package com.luwei.model.banner.pojo.cms;

import com.luwei.model.banner.envm.BannerTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Author: huanglp
 * Date: 2018-12-06
 */
@Data
public class BannerAddDTO {

    @NotNull
    @ApiModelProperty(value = "跳转服务ID")
    private Integer jumpId;

    @NotNull
    @ApiModelProperty(value = "跳转服务名称")
    private String jumpName;

    @NotNull
    @ApiModelProperty(value = "图片")
    private String bannerPicture;

    @NotNull
    @ApiModelProperty(value = "轮播图类型")
    private BannerTypeEnum bannerType;

}
