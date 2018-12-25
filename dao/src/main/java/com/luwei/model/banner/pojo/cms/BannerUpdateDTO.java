package com.luwei.model.banner.pojo.cms;

import com.luwei.model.banner.envm.BannerTypeEnum;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BannerUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @ApiModelProperty(value = "主键ID")
    private Integer bannerId;

    @NotBlank
    @ApiModelProperty(value = "图片")
    private String picture;

    @NotNull
    @ApiModelProperty(value = "跳转服务ID")
    private Integer jumpId;

    @NotBlank
    @ApiModelProperty(value = "跳转服务名称")
    private String jumpName;

    @NotNull
    @ApiModelProperty(value = "服务类型 0-课程 1-托管")
    private ServiceTypeEnum serviceType;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "权重,越小越大")
    private Integer weight;

    @ApiModelProperty(value = "外链url")
    private String linkUrl;

    @ApiModelProperty(value = "是否为外链 0-否 1-是")
    private Boolean outsideLink;

    @NotNull
    @ApiModelProperty(value = "轮播图类型 0-首页 1-订课 2-点餐")
    private BannerTypeEnum bannerType;

    @NotNull
    @ApiModelProperty(value = "是否显示 0-否 1-是")
    private Boolean display;

}
