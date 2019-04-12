package com.luwei.model.hosting.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingCmsVOS {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "托管ID" )
    @NotNull
    private Integer hostingId;

    @ApiModelProperty(value = "托管名称" )
    private String name;

    @ApiModelProperty(value = "学校名称" )
    private String schoolName;


    @ApiModelProperty(value = "是否设为推荐" )
    private Boolean recommend;

    @ApiModelProperty(value = "课程价格" )
    private BigDecimal price;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    private String introduction;


    private String details;
}
