package com.chenzhihui.blog.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzhihui.blog.dto.*;
import com.chenzhihui.blog.mapper.CategoryMapper;
import com.chenzhihui.blog.mapper.TagMapper;
import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.mapper.ArticleMapper;
import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.util.CommonUtils;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.chenzhihui.blog.constant.CommonConst.FALSE;
import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * <p>
 *  文章 - 服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    /**
     * 1、查询归档文章
     *
     * @return 归档文章列表
     */
    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        // 获取分页数据
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("article_id","article_title","create_time");
        wrapper.eq("is_delete",FALSE);
        wrapper.eq("status",PUBLIC.getStatus());
        Page<Article> articlePage = articleMapper.selectPage(page,wrapper);
        System.out.println(articlePage.getRecords().toString());
        // 这一步相当于将articlePage.getRecords()获得到的列表，提取出我们需要的ArchiveDTO的信息，其余丢弃
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int) articlePage.getTotal());
    }

    /**
     * 2、查询所有文章
     *
     * @return 文章列表
     */
    @Override
    public List<Article> listArticles() {
        List<Article> articleList = new ArrayList<>();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        articleList = baseMapper.selectList(wrapper);
        return articleList;
    }

    /**
     * 3、根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // todo: 推荐文章和最新文章 -> 未使用异步CompletableFuture
        // 查询推荐文章 => 设计到多表查询需要手写SQL语句
        List<ArticleRecommendDTO> recommendArticleList = articleMapper.listRecommendArticles(articleId);
        //查询最新文章
        QueryWrapper<Article> queryWrapperNew = new QueryWrapper<>();
        queryWrapperNew.orderByAsc("create_time");
        queryWrapperNew.last("limit 5");
        List<Article> newsArticleList1 = articleMapper.selectList(queryWrapperNew);
        List<ArticleRecommendDTO> newsArticleList = BeanCopyUtils.copyList(newsArticleList1,ArticleRecommendDTO.class);
        System.out.println(newsArticleList.toString());
        // 通过articleId查找文章信息
        ArticleDTO article = articleMapper.getArticleById(articleId);
        // todo：更新文章浏览量
        //updateArticleViewCount(articleId);
        //查询上一篇文章
        QueryWrapper<Article> queryWrapperLast = new QueryWrapper<>();
        queryWrapperLast.select("article_id","article_title","article_cover");
        queryWrapperLast.eq("is_delete",FALSE);
        queryWrapperLast.eq("status",PUBLIC.getStatus());
        queryWrapperLast.lt("article_id",articleId);
        queryWrapperLast.orderByDesc("article_id");
        queryWrapperLast.last("limit 1");
        Article lastArticle = articleMapper.selectOne(queryWrapperLast);
        article.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        //查询下一篇文章
        QueryWrapper<Article> queryWrapperNext = new QueryWrapper<>();
        queryWrapperNext.select("article_id","article_title","article_cover");
        queryWrapperNext.eq("is_delete",FALSE);
        queryWrapperNext.eq("status",PUBLIC.getStatus());
        queryWrapperNext.gt("article_id",articleId);
        queryWrapperNext.orderByDesc("article_id");
        queryWrapperNext.last("limit 1");
        Article nextArticle = articleMapper.selectOne(queryWrapperNext);
        article.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        // todo：封装点赞量和浏览量
        //封装文章信息
        try{
            article.setRecommendArticleList(recommendArticleList);
            article.setNewestArticleList(newsArticleList);
        }catch (Exception e){
            log.error(StrUtil.format("堆栈信息:{}", ExceptionUtil.stacktraceToString(e)));
        }
        return article;
    }


//    /**
//     * 更新文章浏览量
//     *
//     * @param articleId 文章id
//     */
//    public void updateArticleViewsCount(Integer articleId) {
//        // 判断是否第一次访问，增加浏览量
//        Set<Integer> articleSet = CommonUtils.castSet(Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElseGet(HashSet::new), Integer.class);
//        if (!articleSet.contains(articleId)) {
//            articleSet.add(articleId);
//            session.setAttribute(ARTICLE_SET, articleSet);
//            // 浏览量+1
//            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
//        }
//    }


    /**
     * 4、根据类别条件查询文章列表
     *
     * @param condition 文章列表
     * @return {@link ArticlePreviewListDTO} 文章信息
     */
    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition) {
        // 查询文章
        // 对查询过程中使用对current进行调整，设置为传值的current
        Long current = condition.getLimitCurrent();
        List<ArticlePreviewDTO> articlePreviewDTOList = articleMapper.listArticlesByCondition((current-1)*10, 9L,condition);
        System.out.println("输出查找长度" + articlePreviewDTOList.size());
        // 将对应对articleId与List<TagDTO>对应起来
        for(int i=0; i<articlePreviewDTOList.size(); i++){
            List<Tag> tagList = tagMapper.getTagListById(articlePreviewDTOList.get(i).getArticleId());
            List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList,TagDTO.class);
            articlePreviewDTOList.get(i).setTagDTOList(tagDTOList);
        }
        // 搜索条件对应名（标签或分类名）
        String name;
        if(Objects.nonNull(condition.getCategoryId())){
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().select(Category::getCategoryName)
                    .eq(Category::getCategoryId,condition.getCategoryId())).getCategoryName();
        }else{
            name = tagMapper.selectOne(new LambdaQueryWrapper<Tag>().select(Tag::getTagName)
                    .eq(Tag::getTagId,condition.getTagId())).getTagName();
        }
        return ArticlePreviewListDTO.builder().articlePreviewDTOList(articlePreviewDTOList).name(name).build();
    }


    /**
     * 5、搜索文章
     *
     * @param condition 条件
     * @return {@link Result <ArticleSearchDTO>} 文章列表
     */
    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition) {
        // todo：搜索策略应该要用elasticsearch，但是此处用模糊查询代替
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(condition.getKeywords() != null){
            queryWrapper.like("article_title",condition.getKeywords());
        }
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        // 复制属性
        List<ArticleSearchDTO> articleSearchDTOList = BeanCopyUtils.copyList(articleList,ArticleSearchDTO.class);
        return articleSearchDTOList;
    }

    /**
     * 6、点赞文章
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    @Override
    public void saveArticleLike(Integer articleId) {
        // todo：涉及到redisService

        // 通过articleId查找文章
        // 将点赞的用户与文章绑定 -> 属于点赞状态
    }


}
