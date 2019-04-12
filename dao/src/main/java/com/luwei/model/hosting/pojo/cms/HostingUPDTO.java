package com.luwei.model.hosting.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingUPDTO {

    @ApiModelProperty(value = "托管ID" )
    private Integer hostingId;

    @ApiModelProperty(value = "托管名称" )
    private String name;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @ApiModelProperty(value = "简介" )
    private String introduction;


    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管价格" )
    private BigDecimal price;

    @ApiModelProperty(value = "是否设为推荐" )
    private Boolean recommend;


    @ApiModelProperty(value = "托管封面" )
    private String coverUrl;

    @ApiModelProperty(value = "托管详情" )
    private String details;
}
