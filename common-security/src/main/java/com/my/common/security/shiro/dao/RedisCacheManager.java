package com.my.common.security.shiro.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 实现shiro的{@link CacheManager},负责对缓存器的管理。
 * @author
 * @version 1.0
 * @project common
 * @date 2016/9/7
 */
public class RedisCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("");
        }
        Cache cache =  new RedisCacheDAO(name);
        return cache;
    }
}
