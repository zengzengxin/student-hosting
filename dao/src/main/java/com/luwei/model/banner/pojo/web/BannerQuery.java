package com.luwei.model.banner.pojo.web;

import com.luwei.model.banner.envm.BannerTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-28
 */
@Data
public class BannerQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "轮播图类型不能为空")
    @ApiModelProperty(value = "轮播图类型 0-首页 1-订课 2-点餐")
    private BannerTypeEnum bannerType;

}
