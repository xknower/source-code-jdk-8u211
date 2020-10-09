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
 * Indicates that an annotation type is automatically inherited.
 * // 指示自动继承批注类型。
 * If an Inherited meta-annotation is present on an annotation type declaration,
 * and the user queries the annotation type on a class declaration, and the class declaration has no annotation for this type,
 * then the class's superclass will automatically be queried for the annotation type.
 * // 如果注释类型声明中存在继承的元注释，并且用户在类声明中查询注释类型，并且类声明没有此类型的注释，则将自动查询该类的超类以获取注释类型。
 * This process will be repeated until an annotation for this type is found, or the top of the class hierarchy (Object) is reached.
 * // 此过程将重复，直到找到此类型的批注，或到达类层次结构（对象）的顶部。
 * If no superclass has an annotation for this type, then the query will indicate that the class in question has no such annotation.
 * // 如果没有超类具有此类型的批注，则查询将指示所讨论的类没有此类批注。
 *
 * <p>Note that this meta-annotation type has no effect if the annotated type is used to annotate anything other than a class.
 * // 请注意，如果注释类型用于注释类以外的任何内容，则此元注释类型无效。
 * Note also that this meta-annotation only causes annotations to be inherited from superclasses; annotations on implemented interfaces have no effect.
 * // 还要注意，这个元注释只会导致注释从超类继承；实现接口上的注释没有任何效果。
 *
 * @author  Joshua Bloch
 * @since 1.5
 * @jls 9.6.3.3 @Inherited
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Inherited { // 52 元注解, 表示描述的注解, 是否可以呗子类继承
}
