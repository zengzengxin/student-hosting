package com.luwei.timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luwei.model.course.Course;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.service.course.CourseService;
import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CoursePackageTimer {

    @Resource
    private CoursePackageService coursePackageService;

    @Resource
    private CourseService courseService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void coursePackageTimer() {
        long start = System.currentTimeMillis();
        log.info("===================刷新课程套餐是否过期定时任务启动===================");
        int i = coursePackageService.coursePackageTimer();
        log.info("托管刷新为已过期的有{}条", i);
        long end = System.currentTimeMillis();
        log.info("共耗时{}毫秒", String.valueOf(end - start));
        log.info("===================刷新课程套餐是否过期定时任务结束==================");
    }

    @Scheduled(cron = "0 8 1 * * ?")
    private void refreshCourseTimer() {
        long start = System.currentTimeMillis();
        log.info("===================刷新课程的packageNull字段定时任务启动===================");
        List<Course> list = courseService.list(new QueryWrapper<Course>().lambda()
                .eq(Course::getPackageNull, false));
        list.stream().map(this::refreshCourse).collect(Collectors.toList());

        long end = System.currentTimeMillis();
        log.info("共耗时{}毫秒", String.valueOf(end - start));
        log.info("===================刷新课程的packageNull字段定时任务结束==================");
    }

    private Course refreshCourse(Course course) {
        CoursePackage one = coursePackageService.getOne(new QueryWrapper<CoursePackage>().lambda()
                .eq(CoursePackage::getCourseId, course.getCourseId()));
        if (one == null) {
            course.setPackageNull(true);
            courseService.save(course);
        }
        return course;
    }

}
