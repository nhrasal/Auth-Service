
package com.app.auth.base.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.RedisTemplate;

@Repository
public class RedisRepo {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;


    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValue(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void delKey(final String key) {
        redisTemplate.delete(key);
    }

    public void setValue(final String key, final String value, long expireInSeconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireInSeconds, TimeUnit.SECONDS);
    }

    public List<Object> getRange(final String key) {
        return getRange(key, 0, -1);
    }

    public List<Object> getRange(final String key, long sIndex, long eIndex) {
        return redisTemplate.opsForList().range(key, sIndex, eIndex);
    }

    public void setLeftPush(final String key, final String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public void setLeftPush(final String key, final String value, long expireInSeconds) {
        redisTemplate.opsForList().leftPush(key, value);
        redisTemplate.expire(key, expireInSeconds, TimeUnit.SECONDS);
    }

    public void leftTrim(final String key) {
        leftTrim(key, -1, 0);
    }

    public void leftTrim(final String key, long sIndex, long eIndex) {
        redisTemplate.opsForList().trim(key, sIndex, eIndex);
    }

    public Object leftPop(final String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Long getListCount(final String key) {
        return (Long) redisTemplate.opsForList().size(key);
    }

    public void removeValue(String key) {
        redisTemplate.delete(key);
    }

}
