package com.luwei.Timer;

import com.luwei.model.hosting.Hosting;
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

    // @Scheduled(cron = "0 0 2 * * ?")
    @Scheduled(cron = "0 * * * * ?")
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

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        Hosting hosting = new Hosting();
        hosting.setStartTime(LocalDateTime.now());
        hosting.setEndTime(LocalDateTime.now());
        System.out.println("hosting.getStartTime() "+hosting.getStartTime());
        System.out.println("hosting.getEndTime() "+hosting.getEndTime());

        hosting.setStartTime(hosting.getStartTime().withHour(0).withMinute(0).withSecond(0));
        hosting.setEndTime(hosting.getEndTime().withHour(23).withMinute(59).withSecond(59));

        System.out.println("hosting.getStartTime() "+hosting.getStartTime());
        System.out.println("hosting.getEndTime() "+hosting.getEndTime());

        long end = System.currentTimeMillis();
        System.out.println("共耗时" + (end - start));

        /*LocalDateTime time = LocalDateTime.now();
        System.out.println("LocalDateTime" + time);
        System.out.println("Date" + new Date());
        LocalDateTime localDateTime = time.withHour(0).withMinute(0).withSecond(0);

        System.out.println(localDateTime);
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);
        Date start = dateTime.withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .toDate();
        System.out.println(start);
        Date end = dateTime
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59)
                .toDate();
        System.out.println(end);*/
    }

}
