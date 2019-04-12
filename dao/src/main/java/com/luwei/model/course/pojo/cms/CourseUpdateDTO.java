package com.luwei.model.course.pojo.cms;

import com.luwei.model.coursepackage.pojo.cms.CoursePackageUpdateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "课程ID不能为空" )
    @ApiModelProperty(value = "课程ID" )
    private Integer courseId;

    @NotBlank(message = "课程名称不能为空" )
    @ApiModelProperty(value = "课程名称" )
    private String courseName;

    @NotBlank(message = "课程封面不能为空" )
    @ApiModelProperty(value = "课程封面" )
    private String coverUrl;

    @NotBlank(message = "课程简介不能为空" )
    @ApiModelProperty(value = "课程简介" )
    private String introduction;

    @NotEmpty(message = "课程图片不能为空" )
    @Size(min = 1, max = 3, message = "课程图片最少1张，最多3张" )
    @ApiModelProperty(value = "课程图片ID(最多3张)" )
    private List<String> pictureUrls;

    @NotBlank(message = "课程详情不能为空" )
    @ApiModelProperty(value = "课程详情" )
    private String details;

    @NotNull(message = "教师ID不能为空" )
    @ApiModelProperty(value = "教师ID" )
    private Integer teacherId;

    @NotBlank(message = "教师名称不能为空" )
    @ApiModelProperty(value = "教师名称" )
    private String teacherName;

    @NotNull(message = "所在学校ID不能为空" )
    @ApiModelProperty(value = "所在学校ID" )
    private Integer schoolId;

    @NotBlank(message = "学校名称不能为空" )
    @ApiModelProperty(value = "学校名称" )
    private String schoolName;

    @NotEmpty(message = "课程套餐列表不能为空" )
    @ApiModelProperty(value = "课程套餐列表" )
    private List<CoursePackageUpdateDTO> coursePackageList;

}
