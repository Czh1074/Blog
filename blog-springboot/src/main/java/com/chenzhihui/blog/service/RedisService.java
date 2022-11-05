package com.chenzhihui.blog.service;

/**
 * redis操作
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/4 16:04
 **/

public interface RedisService {

    /**
     * zset添加分数
     *
     * @param key   关键
     * @param value 价值
     * @param score 分数
     * @return {@link Double}
     */
    Double zIncr(String key, Object value, Double score);

    /**
     * 获取zset指定元素分数
     *
     * @param key   关键
     * @param value 价值
     * @return {@link Double}
     */
    Double zScore(String key, Object value);
}
