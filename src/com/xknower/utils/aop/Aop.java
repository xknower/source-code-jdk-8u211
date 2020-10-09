package com.xknower.utils.aop;

import java.lang.annotation.*;

/**
 * 默认日志注解类
 *
 * @author xknower
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aop {
}
