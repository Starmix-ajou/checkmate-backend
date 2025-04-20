package com.starmix.checkmate.adapter.out.cache.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starmix.checkmate.adapter.out.cache.CacheType;
import com.starmix.checkmate.application.port.out.cache.CachePort;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class CacheAdapter implements CachePort {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void save(CacheType cacheType, String key, String value) {
        try {
            redisTemplate.opsForValue().set(cacheType.getKey() + key, value);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(CacheType cacheType, String key, String value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(cacheType.getKey() + key, value);
            redisTemplate.expire(cacheType.getKey() + key, timeout, timeUnit);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String get(CacheType cacheType, String key) {
        try {
            return redisTemplate.opsForValue().get(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveSet(CacheType cacheType, String key, String value) {
        try {
            redisTemplate.opsForSet().add(cacheType.getKey() + key, value);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Set<String> getSet(CacheType cacheType, String key) {
        try {
            return redisTemplate.opsForSet().members(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isInclude(CacheType cacheType, String key, String value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(cacheType.getKey() + key, value));
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean exists(CacheType cacheType, String key) {
        try {
            return redisTemplate.hasKey(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(CacheType cacheType, String key) {
        try {
            redisTemplate.delete(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveObject(CacheType cacheType, String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            String json = objectMapper.writeValueAsString(value);
            save(cacheType, key, json, timeout, timeUnit);
        } catch (JsonProcessingException e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveObject(CacheType cacheType, String key, Object value) {
        try {
            String json = objectMapper.writeValueAsString(value);
            save(cacheType, key, json);
        } catch (JsonProcessingException e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public <T> T getObject(CacheType cacheType, String key) {
        try {
            String json = get(cacheType, key);
            if (json == null) {
                return null;
            }
            return (T) objectMapper.readValue(json, cacheType.getType());
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
