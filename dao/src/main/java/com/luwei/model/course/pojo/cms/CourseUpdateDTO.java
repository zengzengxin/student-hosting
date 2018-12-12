package com.luwei.model.course.pojo.cms;

import com.luwei.model.coursepackage.pojo.cms.CoursePackageUpdateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    @ApiModelProperty(value = "课程ID")
    private Integer courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程封面")
    private String coverUrl;

    @ApiModelProperty(value = "课程简介")
    private String introduction;

    @ApiModelProperty(value = "课程图片ID(最多3张)")
    private List<String> pictureUrls;

    @ApiModelProperty(value = "课程详情")
    private String details;

    @ApiModelProperty(value = "教师ID")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "所在学校ID")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "上架到公众号")
    private Boolean display;

    @ApiModelProperty(value = "课程套餐列表")
    private List<CoursePackageUpdateDTO> coursePackageList;

}
