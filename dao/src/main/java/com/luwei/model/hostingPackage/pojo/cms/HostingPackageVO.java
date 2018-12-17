package com.luwei.model.hostingPackage.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zzx
 * @since 2018-12-17
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingPackageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "托管套餐ID")
    private Integer hostingPackageId;

    @ApiModelProperty(value = "托管ID")
    private Integer hostingId;

    @ApiModelProperty(value = "托管开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "托管结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管价格")
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数")
    private Integer maxNumber;

    @ApiModelProperty(value = "上架状态")
    private String display;

    @ApiModelProperty(value = "是否过期 0-未过期 1-过期 默认为0")
    private String overdue;




}
