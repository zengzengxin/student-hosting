package com.luwei.model.course.pojo.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MyCourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始时间", dataType = "java.lang.Long" )
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", dataType = "java.lang.Long" )
    private LocalDateTime endTime;

}
