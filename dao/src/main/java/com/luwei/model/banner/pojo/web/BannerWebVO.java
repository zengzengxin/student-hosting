package com.luwei.model.banner.pojo.web;

import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BannerWebVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID" )
    private Integer bannerId;

    @ApiModelProperty(value = "图片" )
    private String picture;

    @ApiModelProperty(value = "跳转服务ID" )
    private Integer jumpId;

    @ApiModelProperty(value = "服务类型 0-课程 1-托管" )
    private ServiceTypeEnum serviceType;

    @ApiModelProperty(value = "外链url" )
    private String linkUrl;

    @ApiModelProperty(value = "是否为外链 0-否 1-是" )
    private Boolean outsideLink;

    @ApiModelProperty(value = "01-07新增: 学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "01-07新增: 学校名称" )
    private String schoolName;

}
