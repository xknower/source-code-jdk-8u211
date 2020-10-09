package com.xknower.utils.aop.watch;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 方法运行效率监控注解
 *
 * @author xknower
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Watch {

    /**
     * 描述
     */
    String value();

    /**
     * 超过这个警戒线告警
     */
    long limit() default 1L;

    /**
     * 警戒线单位
     */
    TimeUnit limitUnit() default TimeUnit.SECONDS;
}
