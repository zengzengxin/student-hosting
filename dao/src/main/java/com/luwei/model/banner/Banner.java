package com.luwei.model.banner;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.model.banner.envm.BannerTypeEnum;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_banner" )
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID" )
    @TableId(value = "banner_id", type = IdType.AUTO)
    private Integer bannerId;

    @ApiModelProperty(value = "图片" )
    private String picture;

    @ApiModelProperty(value = "跳转服务ID" )
    private Integer jumpId;

    @ApiModelProperty(value = "跳转服务名称" )
    private String jumpName;

    @ApiModelProperty(value = "权重,越小越大" )
    private Integer weight;

    @ApiModelProperty(value = "外链url" )
    private String linkUrl;

    @ApiModelProperty(value = "是否为外链 0-否 1-是" )
    private Boolean outsideLink;

    @ApiModelProperty(value = "轮播图类型 0-首页 1-订课 2-点餐" )
    private BannerTypeEnum bannerType;

    @ApiModelProperty(value = "是否显示 0-否 1-是" )
    private Boolean display;

    @ApiModelProperty(value = "服务类型 0-课程 1-托管" )
    private ServiceTypeEnum serviceType;

    @ApiModelProperty(value = "创建时间" )
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间" )
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否已删除 0-否 1-是" )
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "01-07新增: 学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "01-07新增: 学校名称" )
    private String schoolName;

}
