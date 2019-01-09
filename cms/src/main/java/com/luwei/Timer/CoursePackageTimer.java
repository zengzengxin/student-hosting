package com.luwei.Timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luwei.common.exception.MessageCodes;
import com.luwei.model.coursepackage.CoursePackage;
import com.luwei.model.coursepackage.CoursePackageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CoursePackageTimer {

    @Resource
    private CoursePackageMapper coursePackageMapper;

     @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0/1 * * * * ?")
    private void coursePackageTimer() {
        coursePackageMapper.coursePackageTimer();
    }

}
