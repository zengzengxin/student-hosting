package com.luwei.Timer;

import com.luwei.service.hosting.HostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class HostingTimer {

    @Resource
    private HostingService hostingService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void coursePackageMapper() {
        long start = System.currentTimeMillis();
        log.info("===================刷新托管是否过期定时任务启动===================");
        int i = hostingService.hostingTimer();
        log.info("托管刷新为已过期的有{}条", i);
        long end = System.currentTimeMillis();
        log.info("共耗时{}毫秒", String.valueOf(end - start));
        log.info("===================刷新托管是否过期定时任务结束==================");
    }

}
