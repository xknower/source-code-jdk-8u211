/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Indicates how long annotations with the annotated type are to be retained.
 * // 指示带批注类型的批注要保留多长时间。
 * If no Retention annotation is present on an annotation type declaration, the retention policy defaults to {@code RetentionPolicy.CLASS}.
 * // 如果 注解描述中, 没有 Retention annotation ，则保留策略默认为 RetentionPolicy
 *
 * <p>A Retention meta-annotation has effect only if the meta-annotated type is used directly for annotation.
 * // 只有当元批注类型直接用于批注时，保留元批注才有效。
 * It has no effect if the meta-annotated type is used as a member type in another annotation type.
 * // 如果元注释类型用作另一个注释类型中的成员类型，则它不起作用。
 *
 * @author  Joshua Bloch
 * @since 1.5
 * @jls 9.6.3.2 @Retention
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Retention { // 46 元注解, 表示描述的注解, 在什么时候使用
    /**
     * Returns the retention policy.
     * @return the retention policy
     */
    RetentionPolicy value();
}
