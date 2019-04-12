package com.luwei.model.coursepackage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
public interface CoursePackageMapper extends BaseMapper<CoursePackage> {

    BigDecimal findMinPriceByCourseId(Integer courseId);

    List<MyCourseVO> findAllByTime(@Param("startTime" ) LocalDateTime startTime,
                                   @Param("endTime" ) LocalDateTime endTime, @Param("teacherId" ) Integer teacherId);

    int coursePackageTimer();
}
