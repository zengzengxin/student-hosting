package com.luwei.model.hostingPackage.pojo.cms;

import io.swagger.annotations.ApiModel;
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
 * @author zzx
 * @since 2018-12-17
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingPackageAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "托管开始时间",dataType = "java.lang.Long")
    @NotNull(message = "托管开始时间不能为空")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "托管结束时间", dataType = "java.lang.Long")
    @NotNull(message = "托管结束时间不能为空")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管价格")
    @NotNull(message = "托管价格不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数")
    @NotNull(message = "最大人数不能为空")
    private Integer maxNumber;

    @ApiModelProperty(value = "上架状态")
    @NotBlank(message = "上架状态不能为空")
    private Boolean display;




}
