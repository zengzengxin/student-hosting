package com.luwei.model.course.pojo.cms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseRecommend implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "课程ID")
    private Integer courseId;

    @NotNull
    @ApiModelProperty(value = "是否设为推荐(true/false)")
    private Boolean recommend;

}
