/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang.annotation;


/**
 * Indicates that a field defining a constant value may be referenced from native code.
 * // 指示可以从本机代码引用定义常量值的字段。
 *
 * The annotation may be used as a hint by tools that generate native header files to determine
 * whether a header file is required, and if so, what declarations it should contain.
 * // 生成本机头文件的工具可以使用注释作为提示，以确定是否需要头文件，如果需要，则应包含哪些声明。
 *
 * @since 1.8
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Native {
}
