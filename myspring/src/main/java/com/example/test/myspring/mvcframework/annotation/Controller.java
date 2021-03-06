package com.example.test.myspring.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @Auther: wwt
 * @Date: 2020/12/28 22:35
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
