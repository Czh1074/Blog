package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.BlogHomeInfoDTO;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.WebsiteConfigVO;

/**
 * 博客信息服务
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/22 19:23
 **/
public interface BlogInfoService {


    /**
     * 1、查看博客信息
     *
     * @return {@link Result <BlogHomeInfoDTO>} 博客信息
     */
    BlogHomeInfoDTO getBlogHomeInfo();

    /**
     * 2、查看关于我信息
     *
     * @return {@link Result<String>} 关于我信息
     */
    String getAbout();

    /**
     * 3、上传访客信息
     */
    void report();

    WebsiteConfigVO getWebsiteConfig();
}
