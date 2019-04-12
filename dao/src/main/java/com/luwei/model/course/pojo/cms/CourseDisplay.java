package com.luwei.model.course.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author huanglp
 * Date: 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseDisplay {

    @NotNull(message = "课程ID不能为空" )
    @ApiModelProperty(value = "课程ID" )
    private Integer courseId;

    @NotNull(message = "是否上架到公众号不能为空" )
    @ApiModelProperty(value = "上架到公众号" )
    private Boolean display;
}
