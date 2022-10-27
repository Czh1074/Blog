package com.chenzhihui.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenzhihui.blog.dto.BlogHomeInfoDTO;
import com.chenzhihui.blog.mapper.*;
import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.pojo.WebsiteConfig;
import com.chenzhihui.blog.service.BlogInfoService;
import com.chenzhihui.blog.service.PageService;
import com.chenzhihui.blog.vo.PageVO;
import com.chenzhihui.blog.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;
import static com.chenzhihui.blog.constant.CommonConst.FALSE;
import static com.chenzhihui.blog.constant.CommonConst.*;

/**
 * 博客信息服务实现类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/22 19:25
 **/
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Resource
    public ArticleMapper articleMapper;
    @Resource
    public CategoryMapper categoryMapper;
    @Resource
    public TagMapper tagMapper;
    @Resource
    public PageMapper pageMapper;
    @Resource
    public PageService pageService;
    @Resource
    public WebsiteConfigMapper websiteConfigMapper;


    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 查询文章信息
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, PUBLIC.getStatus())
                .eq(Article::getIsDelete, FALSE)
        );
        // 查询分类信息(selectCount 参数为null时，返回id不同的个数)
        Integer categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagMapper.selectCount(null);
        // todo：查询访问量 涉及到redis

        // todo:查询网站配置(这个方法是什么意思？)
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();


        // 查询页面图片
        List<PageVO> pageVOList = pageService.listPages();
        // 封装数据 -> builder是建造者模式，频繁调用时，采用该方法
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }

    @Override
    public String getAbout() {
        return "<h1>这是陈志辉，一个来自暨南大学学生的个人博客</h1></br><h2>希望能在一个月内完成</h2>";
    }

    /**
     * 上传访客信息
     */
    @Override
    public void report() {

    }


    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        // 从数据库中加载
        String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
        websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class); //是将Json字符串转化为相应的对象
        return websiteConfigVO;
    }


}
