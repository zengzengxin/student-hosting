package com.luwei.service.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  timeCaculateAnnotation {
    String value() default "";
}
