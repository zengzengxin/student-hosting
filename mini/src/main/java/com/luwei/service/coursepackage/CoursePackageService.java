package com.luwei.service.coursepackage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luwei.model.course.pojo.mini.MyCourseVO;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import com.luwei.module.shiro.service.UserHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: huanglp
 * Date: 2018-12-11
 */
@Service
public class CoursePackageService extends ServiceImpl<CoursePackageMapper, CoursePackage> {

    public List<MyCourseVO> findAllByTime(LocalDateTime startTime, LocalDateTime endTime) {

        Integer teacherId = UserHelper.getUserId();
        return baseMapper.findAllByTime(startTime, endTime, teacherId);

    }
}
