package com.luwei.model.course;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luwei.model.course.pojo.web.CourseWebVO;

import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseWebVO> getCourses(Integer start);
}
