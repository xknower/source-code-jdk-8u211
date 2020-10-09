package com.xknower.lambdastream.func;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * 回调函数使用
 */
public class CallableFunction {

    /**
     * 方式一: 定义回调函数
     */
    private void callableMethod(Map<String, Object> params, Callable<Map<String, Object>> myFunc) {
        // 指定回调函数
        System.out.println("回调函数执行前");
        try {
            Map<String, Object> objectMap = myFunc.call();
            System.out.println("回调函数执行后 : ");
            System.out.println(objectMap);
            System.out.println(params);
        } catch (Exception e) {
            System.out.println("执行方捕获异常");
        }
    }

    /**
     * 方式二: 使用 Function
     * <p>
     * <T, R> 定义输入, 输入
     */
    public Map<String, Object> functionMethod(Map<String, Object> params, Function<Map<String, Object>, Map<String, Object>> func) {
        // 执行方
        params.put("B", "C");
        return func.apply(params);
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("A", "V");
        // 指定方, 调用回调函数
//        new CallableFunction().callableMethod(params, new Callable<Map<String, Object>>() {
//            @Override
//            public Map<String, Object> call() throws Exception {
//                // 实现回调函数
//                System.out.println("TODOSomething");
//                System.out.println(params);
//                return params;
//            }
//        });

        // 调用方, 实现调用函数

        new CallableFunction().functionMethod(params, (e) -> {
            //
            System.out.println(params);
            System.out.println(e);
            return e;
        });
    }

}