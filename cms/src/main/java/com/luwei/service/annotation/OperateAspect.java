package com.luwei.service.annotation;




import com.baomidou.mybatisplus.core.override.PageMapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class OperateAspect {

    long start;

    @Pointcut("@annotation(com.luwei.service.annotation.timeCaculateAnnotation)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint){
        MethodSignature sign = (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        start = System.currentTimeMillis();
        System.out.println("开始===============================");
        System.out.println("打印："+sign.getMethod().getName()+" 开始时间"+new Date(start));

    }

    @After("annotationPointCut()")
    public void after() {
        System.out.print("方法结束");
        long end = System.currentTimeMillis();
        System.out.println("结束===============================");
        System.out.println("结束时间"+new Date(end));
        System.out.println("用时"+(end-start));
    }

}