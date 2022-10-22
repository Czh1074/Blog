package com.chenzhihui.blog.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.chenzhihui.blog.dto.BlogHomeInfoDTO;
import com.chenzhihui.blog.service.BlogInfoService;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客信息控制类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/22 19:11
 **/
@Api(tags = "博客信息模块")
@RestController
public class BlogInfoController {

    @Autowired
    private BlogInfoService blogInfoService;


    /**
     * 1、查看博客信息
     *
     * @return {@link Result<BlogHomeInfoDTO>} 博客信息
     */
    @GetMapping("/")
    public Result<BlogHomeInfoDTO> getBlogHomeInfo(){
        return Result.ok(blogInfoService.getBlogHomeInfo());
    }

    /**
     * todo： 涉及到redis
     * 2、查看关于我信息
     *
     * @return {@link Result<String>} 关于我信息
     */
    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result<String> getAbout() {
        return Result.ok(blogInfoService.getAbout());
    }

    /**
     * todo： 涉及到redis
     * 3、上传访客信息
     *
     * @return {@link Result}
     */
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.ok();
    }

}
