package com.xknower.utils;

/**
 * 回调函数定义
 * <p>
 * The Callback interface is designed to allow for a common, reusable interface
 * to exist for defining APIs that requires a call back in certain situations.
 * <p>
 * 回调接口的设计允许存在一个通用的、可重用的接口，用于定义在某些情况下需要回调的api。
 * <p>
 * Callback is defined with two generic parameters: the first parameter
 * specifies the type of the object passed in to the <code>call</code> method,
 * with the second parameter specifying the return type of the method.
 * <p>
 * 回调由两个泛型参数定义：第一个参数 指定传入调用方法的对象的类型，第二个参数指定方法的返回类型。
 *
 * @param <P> The type of the argument provided to the <code>call</code> method.
 * @param <R> The type of the return type of the <code>call</code> method.
 * @author xknower
 */
@FunctionalInterface
public interface CallBack<P, R> {
    /**
     * The <code>call</code> method is called when required, and is given a
     * single argument of type P, with a requirement that an object of type R
     * is returned.
     *
     * @param param The single argument upon which the returned value should be
     *              determined.
     * @return An object of type R that may be determined based on the provided
     * parameter value.
     */
    R call(P param);
}
