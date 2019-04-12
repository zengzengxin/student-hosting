package com.luwei.model.hosting.pojo.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingVO {

    @ApiModelProperty(value = "托管ID" )
    @TableId(value = "hosting_id", type = IdType.AUTO)
    private Integer hostingId;

    @ApiModelProperty(value = "托管名称" )
    private String name;


    @ApiModelProperty(value = "学校名称" )
    private String schoolName;



    @ApiModelProperty(value = "是否设为推荐(默认为0)" )
    private Boolean recommend;

    @ApiModelProperty(value = "托管价格" )
    private BigDecimal price;
}
