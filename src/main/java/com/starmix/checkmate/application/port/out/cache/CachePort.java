package com.starmix.checkmate.application.port.out.cache;

import com.starmix.checkmate.adapter.out.cache.CacheType;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface CachePort {
    void save(CacheType type, String key, String value);
    void save(CacheType type, String key, String value, long timeout, TimeUnit timeUnit);
    String get(CacheType type, String key);
    boolean isInclude(CacheType type, String key, String value);
    void saveSet(CacheType cacheType, String key, List<?> values);
    void updateSet(CacheType cacheType, String key, List<?> newValues);
    <T> List<T> getSet(CacheType cacheType, String key, Class<T> clazz);
    boolean exists(CacheType type, String key);
    void delete(CacheType type, String key);
    void saveObject(CacheType type, String key, Object value);
    void saveObject(CacheType type, String key, Object value, long timeout, TimeUnit timeUnit);
    <T> T getObject(CacheType cacheType, String key);
}
