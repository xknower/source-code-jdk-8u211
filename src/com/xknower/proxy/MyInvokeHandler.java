package com.xknower.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理对象
 */
public class MyInvokeHandler implements InvocationHandler {

    /**
     * 真正业务对象
     */
    private Object target;

    MyInvokeHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // ... 执行业务方法之前的预处理
        System.out.println("执行业务方法之前的预处理");
        Object result = method.invoke(target, args);
        // ... 执行业务方法之后的后置处理
        System.out.println("执行业务方法之后的后置处理");
        return result;
    }

    public Object getProxy() {
        // 创建代理对象
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }
}
