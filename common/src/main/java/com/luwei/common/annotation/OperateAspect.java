package com.luwei.common.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OperateAspect {

    long start;

    @Pointcut("@annotation(com.luwei.common.annotation.TimeCalculateAnnotation)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before() {
        start = System.currentTimeMillis();
    }

    @After("annotationPointCut()")
    public void after() {
        long end = System.currentTimeMillis();
        log.info("共耗时{}毫秒", String.valueOf(end - start));
    }

}