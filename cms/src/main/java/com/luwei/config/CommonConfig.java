package com.luwei.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CommonConfig {

    public static Predicate<String> paths() {
        return Predicates.alwaysTrue();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

    // Spring Boot 2.0 后以下的转换器暂时不需要
    /*@Bean
    public Converter<String, Date> dateToTimestampConverter() {
        return source -> new Date(Long.parseLong(source, 10));
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeToTimestampConverter() {
        return source -> LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(source, 10)),
                        TimeZone.getDefault().toZoneId());
    }*/

}
