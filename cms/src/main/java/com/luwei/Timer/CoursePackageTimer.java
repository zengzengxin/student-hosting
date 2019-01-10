package com.luwei.Timer;

import com.luwei.service.coursepackage.CoursePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CoursePackageTimer {

    @Resource
    private CoursePackageService coursePackageService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void coursePackageTimer() {
        long start = System.currentTimeMillis();
        log.info("===================刷新课程套餐是否过期定时任务启动===================");
        log.info("当前时间 {}", LocalDateTime.now());
        int i = coursePackageService.coursePackageTimer();
        log.info("托管刷新为已过期的有{}条", i);
        long end = System.currentTimeMillis();
        log.info("当前时间 {} 共耗时{}毫秒", LocalDateTime.now(), String.valueOf(end - start));
        log.info("===================刷新课程套餐是否过期定时任务结束==================");
    }

}
