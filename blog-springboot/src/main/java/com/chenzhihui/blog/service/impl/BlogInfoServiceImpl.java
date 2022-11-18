package com.chenzhihui.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.BlogBackHomeInfoDTO;
import com.chenzhihui.blog.dto.BlogHomeInfoDTO;
import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.mapper.*;
import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.pojo.WebsiteConfig;
import com.chenzhihui.blog.service.BlogInfoService;
import com.chenzhihui.blog.service.PageService;
import com.chenzhihui.blog.service.RedisService;
import com.chenzhihui.blog.service.UserInfoService;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.vo.PageVO;
import com.chenzhihui.blog.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;
import static com.chenzhihui.blog.constant.CommonConst.FALSE;
import static com.chenzhihui.blog.constant.RedisPrefixConst.BLOG_VIEWS_COUNT;
import static com.chenzhihui.blog.constant.CommonConst.*;
import static com.chenzhihui.blog.constant.RedisPrefixConst.*;

/**
 * 博客信息服务实现类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/22 19:25
 **/
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private PageMapper pageMapper;
    @Resource
    private PageService pageService;
    @Resource
    private WebsiteConfigMapper websiteConfigMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserInfoMapper userInfoMapper;


    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 查询文章信息
        // todo 4
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",PUBLIC.getStatus());
        queryWrapper.eq("is_delete",FALSE);
        Integer articleCount = articleMapper.selectCount(queryWrapper);
//        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
//                .eq(Article::getStatus, PUBLIC.getStatus())
//                .eq(Article::getIsDelete, FALSE)
//        );
        // 查询分类信息(selectCount 参数为null时，返回id不同的个数)
        Integer categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagMapper.selectCount(null);
        int realCount;
        Object count = null;
        // 查询访问量
        System.out.println("RedisGet得到什么： " + redisService.get(BLOG_VIEWS_COUNT));
        if(redisService.get(BLOG_VIEWS_COUNT) == null){
            realCount = 0;
            System.out.println("real初始化");
        }else{
            count = redisService.get(BLOG_VIEWS_COUNT);
            // 访问量+1
            realCount = Integer.parseInt(String.valueOf(count));
            System.out.println("count为多少？" + count);
        }
        realCount++;
        redisService.set(BLOG_VIEWS_COUNT,realCount);
        System.out.println("插入成功！realCount为：" + realCount);
        String viewsCount = Optional.ofNullable(realCount).orElse(0).toString();
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();
        // 查询页面图片
        List<PageVO> pageVOList = pageService.listPages();
        // 封装数据 -> builder是建造者模式，频繁调用时，采用该方法
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
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

    /***
     * 返回博客后台管理系统首页信息
     */
    @Override
    public BlogBackHomeInfoDTO getBlogBackHomeInfo() {
        // 访问量
        String viewCount = String.valueOf(redisService.get(BLOG_VIEWS_COUNT));
        // 用户量
        Integer userCount = userInfoMapper.selectCount(null);
        // 文章量
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getIsDelete,FALSE));
        // 分类量
        Integer categoryCount = categoryMapper.selectCount(null);
        // 按照降序查找热度最高的五篇文章
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        // 查询文章标签列表
        List<TagDTO> tagList = BeanCopyUtils.copyList(tagMapper.selectList(null),TagDTO.class);
        BlogBackHomeInfoDTO blogBackHomeInfoDTO = BlogBackHomeInfoDTO.builder()
                .viewCount(viewCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagDTOList(tagList)
                .build();
        if(!CollectionUtils.isEmpty(articleMap)){
            // 查询文章排行
            List<ArticleDTO> articleDTOList = listArticleRank(articleMap);
            blogBackHomeInfoDTO.setArticleList(articleDTOList);
        }

        return blogBackHomeInfoDTO;
    }

    /**
     * 查询文章排行
     *
     * @param articleMap 文章信息
     * @return {@link List<ArticleDTO>} 文章排行
     */
    private List<ArticleDTO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                        .select(Article::getArticleId, Article::getArticleTitle)
                        .in(Article::getArticleId, articleIdList))
                .stream().map(article -> ArticleDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getArticleId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }

}
