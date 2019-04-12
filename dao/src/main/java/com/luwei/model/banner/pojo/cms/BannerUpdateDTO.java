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

    @NotNull(message = "主键ID不能为空" )
    @ApiModelProperty(value = "主键ID" )
    private Integer bannerId;

    @NotBlank(message = "图片地址不能为空" )
    @ApiModelProperty(value = "图片" )
    private String picture;

    @NotNull(message = "跳转服务ID不能为空" )
    @ApiModelProperty(value = "跳转服务ID" )
    private Integer jumpId;

    @NotBlank(message = "跳转服务名称不能为空" )
    @ApiModelProperty(value = "跳转服务名称" )
    private String jumpName;

    @Min(1)
    @NotNull(message = "权重不能为空" )
    @ApiModelProperty(value = "权重,越小越大" )
    private Integer weight;

    @NotNull(message = "服务类型不能为空" )
    @ApiModelProperty(value = "服务类型 0-课程 1-托管" )
    private ServiceTypeEnum serviceType;

    @NotNull(message = "轮播图类型不能为空" )
    @ApiModelProperty(value = "轮播图类型 0-首页 1-订课 2-点餐" )
    private BannerTypeEnum bannerType;

    @NotNull(message = "是否显示不能为空" )
    @ApiModelProperty(value = "是否显示 0-否 1-是" )
    private Boolean display;

    @ApiModelProperty(value = "外链url" )
    private String linkUrl;

    @ApiModelProperty(value = "是否为外链 0-否 1-是" )
    private Boolean outsideLink;

    @NotNull(message = "学校ID不能为空" )
    @ApiModelProperty(value = "01-07新增: 学校ID" )
    private Integer schoolId;

}
