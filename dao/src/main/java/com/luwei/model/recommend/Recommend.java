package com.luwei.model.recommend;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luwei.model.recommend.envm.ServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_recommend" )
public class Recommend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推荐表ID" )
    @TableId(value = "recommend_id", type = IdType.AUTO)
    private Integer recommendId;

    @ApiModelProperty(value = "服务ID" )
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称" )
    private String serviceName;

    @ApiModelProperty(value = "服务价格" )
    private BigDecimal servicePrice;

    @ApiModelProperty(value = "服务简介" )
    private String serviceIntroduction;

    @ApiModelProperty(value = "服务图片地址" )
    private String serviceCoverUrl;

    @ApiModelProperty(value = "权重" )
    private Integer weight;

    @ApiModelProperty(value = "学校ID" )
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @ApiModelProperty(value = "服务类型" )
    private ServiceTypeEnum serviceType;

    @ApiModelProperty(value = "课程开始时间" )
    private LocalDateTime startTime;

    @ApiModelProperty(value = "课程结束时间" )
    private LocalDateTime endTime;

    @ApiModelProperty(value = "创建时间" )
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间" )
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除(默认为0)" )
    @TableLogic
    private Boolean deleted;

}
