package com.luwei.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeCalculateAnnotation {
    String value() default "";
}
