package com.chenzhihui.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 博客启动类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/19 18:09
 **/
@MapperScan("com.chenzhihui.blog.mapper") //批量注入mybatis映射器（DAO接口）的注解
@SpringBootApplication //开启自动配置
@EnableScheduling //通过@EnableScheduling注解开启对计划任务的支持
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

//    // TODO 这个是做什么用的？？
//    @Bean
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

}
