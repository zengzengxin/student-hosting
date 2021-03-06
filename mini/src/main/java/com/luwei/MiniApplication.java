package com.luwei;

import com.luwei.common.holder.SpringBeanHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author luwei
 **/
@EnableSwagger2
@SpringBootApplication
public class MiniApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MiniApplication.class, args);
        // 将applicationContext的引用放到SpringBeanHolder中.
        SpringBeanHolder.setApplicationContext(context);
    }

}