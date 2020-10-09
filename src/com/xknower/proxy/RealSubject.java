package com.xknower.proxy;

/**
 * 业务实现类, 实现接口
 *
 * @author xknower
 */
public class RealSubject implements Subject {

    /**
     * 实际业务处理方法
     */
    @Override
    public void operation() {
        System.out.println("\n执行业务 ...\n");
    }
}
