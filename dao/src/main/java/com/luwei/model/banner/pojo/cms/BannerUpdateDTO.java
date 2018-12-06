package com.luwei.model.banner.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: huanglp
 * Date: 2018-12-06
 */
@Data
public class BannerUpdateDTO {

    @ApiModelProperty(value = "主键ID")
    private Integer bannerId;

    @ApiModelProperty(value = "跳转服务ID")
    private Integer jumpId;

    @ApiModelProperty(value = "跳转服务名称")
    private String jumpName;

    @ApiModelProperty(value = "图片")
    private String bannerPicture;

    @ApiModelProperty(value = "轮播图类型")
    private Boolean bannerType;

}
