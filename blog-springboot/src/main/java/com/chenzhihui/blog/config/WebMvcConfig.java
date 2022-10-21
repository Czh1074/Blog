package com.chenzhihui.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc配置
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/19 20:32
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Bean
//    public WebSecurityHandler getWebSecurityHandler() {
//        return new WebSecurityHandler();
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PageableHandlerInterceptor());
//        registry.addInterceptor(getWebSecurityHandler());
//    }


}
