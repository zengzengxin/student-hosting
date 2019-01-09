package com.luwei.Timer;

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

    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0/1 * * * * ?")
    private void coursePackageTimer() {
        coursePackageService.coursePackageTimer();
    }

}
