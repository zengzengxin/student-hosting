package com.luwei.model.banner.pojo.cms;

import com.luwei.model.banner.envm.BannerTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BannerQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "轮播图类型 0-首页 1-订课 2-点餐")
    private BannerTypeEnum bannerType;

}
