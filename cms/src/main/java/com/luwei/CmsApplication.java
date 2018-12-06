package com.luwei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * <p>
 *
 * @author luwei
 **/
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.luwei.mapper")
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }


}
