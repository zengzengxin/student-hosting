package com.luwei.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeCalculateAnnotation {
    String value() default "";
}
