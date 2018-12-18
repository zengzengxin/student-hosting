package com.luwei.model.hostingPackage.pojo.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* @author zzx
* @since 2018-12-17
*/
@ApiModel(value ="")
@Data
    @EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingPackageUpdateDTO implements Serializable {

private static final long serialVersionUID = 1L;

                
    @ApiModelProperty(value = "托管ID")
                                    @NotNull(message = "托管ID不能为空")
        @Range(min = 0, max = 20000, message = "托管ID范围必须在【{min},{max}】之间")
    private Integer hostingId;

    @ApiModelProperty(value = "托管开始时间")
                                    @NotNull(message = "托管开始时间不能为空")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "托管结束时间")
                                    @NotNull(message = "托管结束时间不能为空")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管价格")
                                    @NotNull(message = "托管价格不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数")
                                    @NotNull(message = "最大人数不能为空")
        @Range(min = 0, max = 20000, message = "最大人数范围必须在【{min},{max}】之间")
    private Integer maxNumber;

    @ApiModelProperty(value = "上架状态")
                                    @NotBlank(message = "上架状态不能为空")
        @Size(min = 1, max = 12, message = "上架状态长度必须在【{min}和{max}】之间")
    private String display;

    @ApiModelProperty(value = "是否过期 0-未过期 1-过期 默认为0")
                                    @NotBlank(message = "是否过期 0-未过期 1-过期 默认为0不能为空")
        @Size(min = 1, max = 12, message = "是否过期 0-未过期 1-过期 默认为0长度必须在【{min}和{max}】之间")
    private String overdue;

    @ApiModelProperty(value = "创建时间")
                                    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
                                    @NotNull(message = "修改时间不能为空")
    private LocalDateTime updateTime;


                


}
