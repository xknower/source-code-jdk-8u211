/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * Annotation retention policy.  The constants of this enumerated type
 * describe the various policies for retaining annotations.  They are used
 * in conjunction with the {@link Retention} meta-annotation type to specify
 * how long annotations are to be retained.
 *
 * @author Joshua Bloch
 * @since 1.5
 */
public enum RetentionPolicy { // 37
    /**
     * Annotations are to be discarded by the compiler. // 注解只存在于源码阶段, 编译时编译器丢弃该注解。
     */
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler but need not be retained by the VM at run time.
     * This is the default behavior.
     * // 注解只存在于类文件中, 运行加载时, 不会加载到内存, 即不需要在运行时由VM保留。
     */
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and retained by the VM at run time, so they may be read reflectively.
     * // 注释将由编译器记录在类文件中，并在运行时由VM保留，因此可以反射地读取它们。因此需要反射获取注解信息时, 获取必须设置为此属性。
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
