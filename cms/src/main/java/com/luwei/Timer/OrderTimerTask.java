package com.luwei.Timer;

import com.luwei.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Author: huanglp
 * Date: 2019-01-09
 */
@Component
@Slf4j
public class OrderTimerTask {

    @Resource
    private OrderService orderService;

    @Scheduled(cron = "0 0 2 * * ?")
    private void refreshOrderStatus() {
        log.info("===================刷新订单状态定时任务启动===================");
        log.info("当前时间 {}", LocalDateTime.now());
        long start = System.currentTimeMillis();

        // 刷新未支付订单的状态
        long refreshNotPaidOrderNumber = orderService.refreshNotPaidOrderStatus();
        log.info("未支付订单状态->刷新为已过期的有{}条", refreshNotPaidOrderNumber);
        // 刷新已支付订单的状态
        long refreshPaidOrderNumber = orderService.refreshPaidOrderStatus();
        log.info("已支付订单状态->刷新为已完成的有{}条", refreshPaidOrderNumber);
        log.info("总共刷新了{}条订单状态", refreshPaidOrderNumber + refreshNotPaidOrderNumber);

        long end = System.currentTimeMillis();
        log.info("当前时间 {} 共耗时{}毫秒", LocalDateTime.now(), String.valueOf(end - start));
        log.info("===================刷新订单状态定时任务结束===================");
    }

}
