package com.starmix.checkmate.application.port.out.redis;

import com.starmix.checkmate.adapter.out.redis.RedisType;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisPort {
    void save(RedisType type, String key, String value);
    void save(RedisType type, String key, String value, long timeout, TimeUnit timeUnit);
    String get(RedisType type, String key);
    boolean isInclude(RedisType type, String key, String value);
    void saveSet(RedisType redisType, String key, List<?> values);
    void saveSet(RedisType redisType, String key, List<?> values, long timeout, TimeUnit timeUnit);
    void updateSet(RedisType redisType, String key, List<?> newValues);
    <T> List<T> getSet(RedisType redisType, String key, Class<T> clazz);
    boolean exists(RedisType type, String key);
    void delete(RedisType type, String key);
    void saveObject(RedisType type, String key, Object value);
    void saveObject(RedisType type, String key, Object value, long timeout, TimeUnit timeUnit);
    <T> T getObject(RedisType redisType, String key);
}
