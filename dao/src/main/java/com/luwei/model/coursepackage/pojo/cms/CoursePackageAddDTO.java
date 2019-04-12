package com.luwei.model.coursepackage.pojo.cms;

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
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CoursePackageAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程开始时间", dataType = "java.lang.Long" )
    @NotNull(message = "课程开始时间不能为空" )
    private LocalDateTime startTime;

    @ApiModelProperty(value = "课程结束时间", dataType = "java.lang.Long" )
    @NotNull(message = "课程结束时间不能为空" )
    private LocalDateTime endTime;

    @ApiModelProperty(value = "课程价格" )
    @NotNull(message = "课程价格不能为空" )
    private BigDecimal price;

    @ApiModelProperty(value = "最大人数" )
    @NotNull(message = "最大人数不能为空" )
    private Integer maxNumber;

    @ApiModelProperty(value = "上架状态" )
    @NotNull(message = "上架状态不能为空" )
    private Boolean display;

    @ApiModelProperty(value = "课室" )
    @NotBlank(message = "课室不能为空" )
    private String classroom;

    @ApiModelProperty(value = "上课时间", dataType = "java.lang.Long" )
    @NotNull(message = "上课时间不能为空" )
    private LocalDateTime classTime;

    @ApiModelProperty(value = "下课时间", dataType = "java.lang.Long" )
    @NotNull(message = "下课时间不能为空" )
    private LocalDateTime quittingTime;

}
