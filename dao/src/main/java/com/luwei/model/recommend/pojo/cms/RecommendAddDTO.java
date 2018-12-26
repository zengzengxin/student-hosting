package com.luwei.model.recommend.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
// TODO 手动格式化 VO DTO类删除@TableName @TableId等, 字段根据业务修改 (删除该条)
@TableName("tb_recommend")
public class RecommendAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推荐表ID")
    @TableId(value = "recommend_id", type = IdType.AUTO)
    @NotNull(message = "推荐表ID不能为空")
    private Integer recommendId;

    @ApiModelProperty(value = "服务ID")
    @NotNull(message = "服务ID不能为空")
    private Integer serviceId;

    @ApiModelProperty(value = "服务名称")
    @NotBlank(message = "服务名称不能为空")
    private String serviceName;

    @ApiModelProperty(value = "服务价格")
    @NotNull(message = "服务价格不能为空")
    private BigDecimal servicePrice;

    @ApiModelProperty(value = "服务简介")
    @NotBlank(message = "服务简介不能为空")
    private String serviceIntroduction;

    @ApiModelProperty(value = "服务图片地址")
    @NotBlank(message = "服务图片地址不能为空")
    private String serviceCoverUrl;

    @ApiModelProperty(value = "权重")
    @NotNull(message = "权重不能为空")
    private Integer weight;

    @ApiModelProperty(value = "创建时间")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除(默认为0)")
    @TableLogic
    @NotNull(message = "是否删除不能为空")
    private Boolean deleted;

}
