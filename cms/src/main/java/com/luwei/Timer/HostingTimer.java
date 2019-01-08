package com.luwei.Timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.hosting.Hosting;
import com.luwei.service.hosting.HostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HostingTimer {

    @Resource
    HostingService hostingService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void coursePackageMapper(){
        List<Hosting> hostings = hostingService.list(new QueryWrapper<Hosting>());
        hostings.stream().map(this::overdue).collect(Collectors.toList());
    }

    private  Hosting overdue(Hosting hosting) {
        if (hosting.getEndTime().compareTo(LocalDateTime.now())<0 && hosting.getOverdue() == false){
            hosting.setOverdue(true);
            Assert.isTrue(hostingService.updateById(hosting), MessageCodes.HOSTING_IS_UPDATE_ERROR);
            log.info("定時器更新數據庫，設置託管是否過期: {}", hosting);
        }
        return hosting;
    }
}
