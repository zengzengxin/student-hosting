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
    HostingService hostingService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void coursePackageMapper() {
        hostingService.hostingTimer();
    }

}
