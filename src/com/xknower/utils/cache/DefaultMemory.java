package com.xknower.utils.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 内存缓存对象
 *
 * @param <K>
 * @param <V>
 * @author xknower
 */
public class DefaultMemory<K, V> implements IMemory<K, V> {

    /**
     * 内存缓存
     */
    private Cache<K, V> cache;

    /**
     * 只使用内存, 且没有过期时间
     */
    public DefaultMemory() {
        this(0, null, null);
    }

    /**
     * 使用内存及REDIS
     *
     * @param expireTime      过期时间
     * @param expireTimeUnit  过期时间范围
     * @param removalListener 过期回调处理器
     */
    public DefaultMemory(int expireTime, TimeUnit expireTimeUnit, RemovalListener<K, V> removalListener) {
        if (expireTimeUnit != null) {
            // 是否带过期时间和过期规则
            CacheBuilder cacheBuilder =
                    CacheBuilder
                            .newBuilder()
                            .expireAfterWrite(expireTime, expireTimeUnit);

            //
            if (removalListener != null) {
                cacheBuilder.removalListener(removalListener);
            }

            //
            this.cache = cacheBuilder.build();
        } else {

            // 不带过期时间
            this.cache = CacheBuilder.newBuilder().build();
        }
    }

    @Override
    public V get(K k) {
        V v = null;
        if (cache != null) {
            v = cache.getIfPresent(k);
        }
        return v;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return cache.asMap().entrySet();
    }

    @Override
    public void set(K k, V v) {
        if (cache != null) {
            cache.put(k, v);
        }
    }

    @Override
    public void set(K k, V v, int expireTime, TimeUnit expireTimeUnit) {
        if (cache != null) {
            cache.put(k, v);
        }
    }

    @Override
    public void delete(K k) {
        if (cache != null) {
            cache.invalidate(k);
        }
    }
}
