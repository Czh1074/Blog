package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redis服务实现类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/4 16:13
 **/
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Double zIncr(String key, Object value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    @Override
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }
}
