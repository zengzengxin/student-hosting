package com.luwei.model.teacher.pojo.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2018-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "开始时间", dataType = "java.lang.Long")
    private LocalDateTime startTime;

    @NotNull
    @ApiModelProperty(value = "结束时间", dataType = "java.lang.Long")
    private LocalDateTime endTime;

}
