package com.luwei.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@Configuration
public class CommonConfig {

    public static Predicate<String> paths() {
        return Predicates.alwaysTrue();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer () {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    public Converter<String, Date> dateToTimestampConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                return new Date(Long.parseLong(source, 10));
            }
        };
    }

}
