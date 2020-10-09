package com.xknower.utils.cache;

import java.util.concurrent.TimeUnit;

/**
 * 缓存存储对象
 *
 * @author xknower
 */
public interface ICache<K, V> {

    /**
     * 根据KEY查询值
     *
     * @param k KEY
     * @return V 值
     */
    V get(K k);

    /**
     * 缓存值
     *
     * @param k KEY
     * @param v Value
     */
    void set(K k, V v);

    /**
     * 缓存值, 并设置过期时间
     *
     * @param k              KEY
     * @param v              Value
     * @param expireTime     过期时间
     * @param expireTimeUnit 过期时间单位
     */
    void set(K k, V v, int expireTime, TimeUnit expireTimeUnit);

    /**
     * 删除缓存
     *
     * @param k KEY
     */
    void delete(K k);
}
