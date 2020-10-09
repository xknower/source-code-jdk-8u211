package com.xknower.utils.queue.impl;

import com.xknower.utils.queue.entity.IPackage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析服务
 *
 * @author yuan
 */
public final class ParserFactory {

    /**
     * 消息解析映射器
     */
    private static Map<String, Class<? extends IPackage>> classMap = new ConcurrentHashMap<>();

    /**
     * 传入消息对象直接进行解析
     */
    public static <T extends IPackage> T parser(byte[] msgBytes, Class<T> clazz) {
        Class<? extends IPackage> msgClass = classMap.computeIfAbsent(clazz.getName(), ParserFactory::build);
        try {
            IPackage msg = msgClass.newInstance();
            msg.readFromBytes(msgBytes);
            return (T) msg;
        } catch (InstantiationException e) {
            System.out.println(String.format("不能实例化 => %s", clazz.getName()));
        } catch (IllegalAccessException e) {
            System.out.println(String.format("非法实例化 => %s", clazz.getName()));
        }
        throw new RuntimeException("100002");
    }

    private static Class<? extends IPackage> build(String clazzName) {
        try {
            return (Class<? extends IPackage>) Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            System.out.println(String.format("加载路径错误 => %s", clazzName));
            throw new RuntimeException("100001");
        }
    }
}
