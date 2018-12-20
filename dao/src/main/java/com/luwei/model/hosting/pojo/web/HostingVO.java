package com.luwei.model.hosting.pojo.web;

import com.luwei.model.hosting.envm.HostingTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zzx
 * @since 2018-12-17
 */
@ApiModel(value = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HostingVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "托管ID")
    private Integer hostingId;

    @ApiModelProperty(value = "托管名称")
    private String name;

    @ApiModelProperty(value = "托管封面")
    private String coverUrl;//cover_url

    @ApiModelProperty(value = "托管简介")
    private String introduction;

    @ApiModelProperty(value = "托管类型")
    private HostingTypeEnum hostingType;

    @ApiModelProperty(value = "托管详情")
    private String details;

    @ApiModelProperty(value = "教师ID")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "所在学校id")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "上架到公众号")
    private Boolean display;

    @ApiModelProperty(value = "是否设为推荐(默认为0)")
    private Boolean recommend;


    @ApiModelProperty(value = "托管开始时间",dataType = "java.lang.Long")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "托管结束时间",dataType = "java.lang.Long")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "托管价格")
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数")
    private Integer maxNumber;

    @NotNull
    @ApiModelProperty(value = "课程图片ID(最多3张)")
    private List<String> pictureUrls;

}
