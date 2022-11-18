package com.chenzhihui.blog.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.chenzhihui.blog.dto.BlogBackHomeInfoDTO;
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

    /**
     * 4、查询博客后台首页信息
     *
     * @retuen {@link Result<>}
     * */
    @GetMapping("/admin")
    public Result<BlogBackHomeInfoDTO> getBlogBackHomeInfo(){
        System.out.println("访问到我啦-/admin");
        return Result.ok(blogInfoService.getBlogBackHomeInfo());
    }

}
