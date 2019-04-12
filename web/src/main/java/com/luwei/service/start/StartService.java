package com.luwei.service.start;

import com.luwei.timer.TokenTimerTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: huanglp
 * Date: 2019-01-10
 */
@Service
public class StartService implements CommandLineRunner {

    @Resource
    private TokenTimerTask tokenTimerTask;

    @Override
    public void run(String... args) {
        /*tokenTimerTask.getTokenAndTicket();*/
    }

}
