package org.yohm.springcloud.fileupload.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 功能简述
 * (redis相关操作方法)
 *
 * @author 海冰
 * @date 2019-06-16
 * @since 1.0.0
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final Logger LOG= LoggerFactory.getLogger(RedisService.class);

    /**
     * 设置超时时间
     * @param key 不能为null
     * @param timeout 单位:秒
     * @return
     */
    public boolean setExpireTime(String key, Long timeout){
        return redisTemplate.expire(key,timeout, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        if(key == null){
            return false;
        }else{
            return redisTemplate.hasKey(key);
        }
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 从redis中获取值
     * @param key
     * @return
     */
    public Object get(String key){
        try {
            if(key==null){
                return null;
            }else{
                return redisTemplate.opsForValue().get(key);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 存入redis,默认超时时间24h
     * @param key
     * @param value
     */
    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value,1,TimeUnit.DAYS);
    }

    /**
     * 存入redis,指定超时时间
     * @param key
     * @param value
     * @param timeout 单位:秒
     */
    public void set(String key,Object value,Long timeout){
        redisTemplate.opsForValue().set(key,value,timeout,TimeUnit.SECONDS);
    }

    public Long lLeftPush(String key, Object value){
        Long result = 0L;
        try {
            result = redisTemplate.opsForList().leftPush(key,value);
            redisTemplate.expire(key,1,TimeUnit.DAYS);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 存入redis队列,默认超时时间24h
     * @param key
     * @param values
     */
    public Long lLeftPushAll(String key, List<?> values){
        Long result = 0L;
        try {
            result = redisTemplate.opsForList().leftPushAll(key,values.toArray());
            redisTemplate.expire(key,1,TimeUnit.DAYS);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return result;
    }

    public Long lRightPush(String key, Object value){
        Long result = 0L;
        try {
            result = redisTemplate.opsForList().rightPush(key,value);
            redisTemplate.expire(key,1,TimeUnit.DAYS);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return result;
    }

    public Object lLeftPop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object lRightPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    public int lSize(String key){
        return Math.toIntExact(redisTemplate.opsForList().size(key));
    }
}
