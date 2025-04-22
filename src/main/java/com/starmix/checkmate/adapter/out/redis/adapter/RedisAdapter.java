package com.starmix.checkmate.adapter.out.redis.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starmix.checkmate.adapter.out.redis.RedisType;
import com.starmix.checkmate.application.port.out.redis.RedisPort;
import com.starmix.checkmate.global.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class RedisAdapter implements RedisPort {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void save(RedisType cacheType, String key, String value) {
        try {
            redisTemplate.opsForValue().set(cacheType.getKey() + key, value);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(RedisType cacheType, String key, String value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(cacheType.getKey() + key, value);
            redisTemplate.expire(cacheType.getKey() + key, timeout, timeUnit);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String get(RedisType cacheType, String key) {
        try {
            return redisTemplate.opsForValue().get(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveSet(RedisType cacheType, String key, List<?> values) {
        try {
            for (Object value : values) {
                String json = objectMapper.writeValueAsString(value);
                redisTemplate.opsForSet().add(cacheType.getKey() + key, json);
            }
        } catch (JsonProcessingException e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public <T> List<T> getSet(RedisType cacheType, String key, Class<T> clazz) {
        try {
            Set<String> jsonSet = redisTemplate.opsForSet().members(cacheType.getKey() + key);
            if (jsonSet == null || jsonSet.isEmpty()) {
                return List.of();
            }
            List<T> result = new ArrayList<>();
            for (String json : jsonSet) {
                result.add(objectMapper.readValue(json, clazz));
            }
            return result;
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isInclude(RedisType cacheType, String key, String value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(cacheType.getKey() + key, value));
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean exists(RedisType cacheType, String key) {
        try {
            return redisTemplate.hasKey(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(RedisType cacheType, String key) {
        try {
            redisTemplate.delete(cacheType.getKey() + key);
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveObject(RedisType cacheType, String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            String json = objectMapper.writeValueAsString(value);
            save(cacheType, key, json, timeout, timeUnit);
        } catch (JsonProcessingException e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveObject(RedisType cacheType, String key, Object value) {
        try {
            String json = objectMapper.writeValueAsString(value);
            save(cacheType, key, json);
        } catch (JsonProcessingException e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public <T> T getObject(RedisType cacheType, String key) {
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

    @Override
    public void updateSet(RedisType cacheType, String key, List<?> newValues) {
        try {
            redisTemplate.delete(cacheType.getKey() + key);

            for (Object value : newValues) {
                String json = objectMapper.writeValueAsString(value);
                redisTemplate.opsForSet().add(cacheType.getKey() + key, json);
            }
        } catch (Exception e) {
            throw new CustomException("Redis Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
