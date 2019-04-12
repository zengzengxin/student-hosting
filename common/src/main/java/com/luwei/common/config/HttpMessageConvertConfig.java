package com.luwei.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.Charset;

/**
 * @Author: david
 * @Description: 显式配置转换使用fastjson转换器
 * @Date； 2018/11/24
 **/
@Configuration
public class HttpMessageConvertConfig {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();

        SerializerFeature[] features = new SerializerFeature[]{
        };
        config.setSerializerFeatures(features);
        config.setCharset(Charset.forName("UTF-8" ));

        converter.setFastJsonConfig(config);
        HttpMessageConverter<?> mConverter = converter;

        return new HttpMessageConverters(mConverter);

    }
}
