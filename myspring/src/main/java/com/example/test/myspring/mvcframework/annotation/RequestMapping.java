package com.example.test.myspring.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Auther: wwt
 * @Date: 2020/12/28 22:36
 * @Description:
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
