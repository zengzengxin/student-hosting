package com.luwei.Timer;

import com.luwei.common.annotation.TimeCalculateAnnotation;
import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CoursePackageTimer {

    @Resource
    private CoursePackageService coursePackageService;

    @TimeCalculateAnnotation
    @Scheduled(cron = "0 0 1 * * ?")
    private void coursePackageTimer() {
        long start = System.currentTimeMillis();
        log.info("===================刷新课程套餐是否过期定时任务启动===================");
        int i = coursePackageService.coursePackageTimer();
        log.info("托管刷新为已过期的有{}条", i);
        long end = System.currentTimeMillis();
        log.info("===================刷新课程套餐是否过期定时任务结束==================");
    }

}
