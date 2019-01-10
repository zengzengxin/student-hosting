package com.luwei.service.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class OperateAspect {

    long start;

    @Pointcut("@annotation(com.luwei.service.annotation.TimeCalculateAnnotation)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before() {
        start = System.currentTimeMillis();
    }

    @After("annotationPointCut()")
    public void after() {
        long end = System.currentTimeMillis();
        log.info("当前时间 {} 共耗时{}毫秒", LocalDateTime.now(), String.valueOf(end - start));
    }

}