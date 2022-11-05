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

    // 关于的撰写
    @Override
    public String getAbout() {
        return "###  关于我\n" +
                "\n" +
                "- 一个碌碌无为的躺平研一学生\n" +
                "- 在学习全栈Java的路上......\n" +
                "\n" +
                "> 为什么要学习Java\n" +
                ">\n" +
                "> \u200B\t从大一到大四毕业，整个大学生涯好像什么都做了，又好像什么都没做，专业能录不够\n" +
                ">\n" +
                "> \u200B\t22年考上研究生，虽然都在说互联网寒冬，也确实是，还是希望自己能静下心来，在这三年好好沉淀自己！而Java是最喜欢的语言，所以脚踏实地的去学习就好！\n" +
                "\n" +
                "###  关于本站\n" +
                "\n" +
                "- 从github上看到的这个项目，也满足自己一直想拥有个人博客的一个执念\n" +
                "- 该博客主要是参考hexo中的butterfly主题\n" +
                "- 仅以此来记录自己的编程生涯";
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
