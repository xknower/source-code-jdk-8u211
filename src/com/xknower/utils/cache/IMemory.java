package com.xknower.utils.cache;

import java.util.Map;
import java.util.Set;

/**
 * 内存缓存
 *
 * @author xknower
 */
public interface IMemory<K, V> extends ICache<K, V> {

    /**
     * 返回缓存所有 KEY
     *
     * @return KEYS
     */
    Set<Map.Entry<K, V>> entrySet();
}
