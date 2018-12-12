package com.luwei.model.coursepackage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luwei.model.coursepackage.pojo.cms.CoursePackageVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
public interface CoursePackageMapper extends BaseMapper<CoursePackage> {

    List<CoursePackageVO> findAllByCourseId(@Param("courseId") Integer courseId);

    BigDecimal findMinPriceByCourseId(Integer courseId);
}
