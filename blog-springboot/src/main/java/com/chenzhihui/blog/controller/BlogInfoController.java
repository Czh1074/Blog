package com.chenzhihui.blog.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.chenzhihui.blog.dto.BlogBackHomeInfoDTO;
import com.chenzhihui.blog.dto.BlogHomeInfoDTO;
import com.chenzhihui.blog.service.BlogInfoService;
import com.chenzhihui.blog.vo.AboutVO;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.WebsiteConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        System.out.println("查询到后台主页");
        return Result.ok(blogInfoService.getBlogHomeInfo());
    }

    /**
     * 查看博客信息后台管理系统-首页信息
     *
     * @return {@link Result<BlogBackHomeInfoDTO>} 博客信息
     */
    @GetMapping("/admin")
    public Result<BlogBackHomeInfoDTO> getBlogBackHome(){
        System.out.println("查询到后台主页");
        return Result.ok(blogInfoService.getBlogBackHome());
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
     * 修改查看关于我信息
     *
     * @param  aboutVO 关于我的信息内容部分
     */
    @ApiOperation(value = "查看关于我信息")
    @PutMapping ("/admin/updateAbout")
    public Result<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        blogInfoService.updateAbout(aboutVO.getAboutContent());
        return Result.ok();
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
     * 4、查询博客后台-管理首页信息
     *
     * @retuen {@link Result<WebsiteConfigVO>}
     * */
    @GetMapping("/admin/websiteConfig")
    public Result<WebsiteConfigVO> getBlogBackHomeInfo(){
        System.out.println("访问到我啦-/admin");
        return Result.ok(blogInfoService.getBlogBackHomeInfo());
    }

    /**
     * 5、修改博客配置信息
     *
     * @param websiteConfigVO 网站配置
     * @retuen {@link Result<?>}
     * */
    @PutMapping("/admin/updateWebsiteConfig")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO){
        blogInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.ok();
    }

}
