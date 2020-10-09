package com.xknower.proxy;

/**
 * JDK 动态代理实现
 * 1>. 定义接口类
 * 2>. 定义业务类并实现接口
 * 3>. 定义代理对象, 实例化代理对象, 传入业务实现类, 通过代理对象获取接口实例, 调用业务方法
 *
 * @author xknower
 */
public class ProxyMain {

    public static void main(String[] args) {
        Subject subject = new RealSubject();

        MyInvokeHandler invokeHandler = new MyInvokeHandler(subject);

        // 获取代理对象
        Subject proxy = (Subject) invokeHandler.getProxy();
        // 调用代理对象的方法, 会调用 MyInvokeHandler.invoke() 方法
        proxy.operation();
    }
}

