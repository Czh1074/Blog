package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.ArchiveDTO;
import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.ArticlePreviewListDTO;
import com.chenzhihui.blog.dto.ArticleSearchDTO;
import com.chenzhihui.blog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface ArticleService extends IService<Article> {

    /**
     * 1、查询归档文章
     *
     * @return 归档文章列表
     */
    PageResult<ArchiveDTO> listArchives();

    /**
     * 2、查询首页文章
     *
     * @return 文章列表
     */
    List<Article> listArticles();

    /**
     * 3、根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 4、根据条件查询文章列表
     *
     * @param condition 文章列表
     * @return {@link ArticlePreviewListDTO} 文章信息
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition);


    /**
     * 5、搜索文章
     *
     * @param condition 条件
     * @return {@link Result <ArticleSearchDTO>} 文章列表
     */
    List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition);

    /**
     * 6、点赞文章
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    void saveArticleLike(Integer articleId);
}
