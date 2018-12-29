package com.luwei;

import com.luwei.common.holder.SpringBeanHolder;
import com.luwei.common.util.WeChatUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * @author luwei
 **/
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class WebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WebApplication.class, args);
        // 将applicationContext的引用放到SpringBeanHolder中.
        SpringBeanHolder.setApplicationContext(context);
    }

    @Resource
    private WeChatUtils weChatUtils;

    @Override
    public void run(String... args) {
        weChatUtils.getToken();
    }
}