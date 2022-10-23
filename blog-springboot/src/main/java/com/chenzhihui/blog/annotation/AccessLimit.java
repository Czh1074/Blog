package com.chenzhihui.blog.annotation;

import java.lang.annotation.*;

/**
 * 接口防刷注解类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/23 10:26
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();
}
