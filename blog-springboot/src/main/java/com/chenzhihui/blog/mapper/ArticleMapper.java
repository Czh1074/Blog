package com.chenzhihui.blog.mapper;

import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.ArticleHomeDTO;
import com.chenzhihui.blog.dto.ArticlePreviewDTO;
import com.chenzhihui.blog.dto.ArticleRecommendDTO;
import com.chenzhihui.blog.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenzhihui.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查看文章的推荐文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    List<ArticleRecommendDTO> listRecommendArticles(@Param("articleId") Integer articleId);

    /**
     * 通过articleId查找文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);


    /**
     * 通过condition查找文章
     *
     * @param condition 查找条件
     * @return 查找文章列表
     */
    List<ArticlePreviewDTO> listArticlesByCondition(Long limitCurrent, Long size, ConditionVO condition);

    /**
     * 2、查询所有文章
     *
     * @return 文章列表
     */
    List<ArticleHomeDTO> getArticleList(Long current, Long size);
}
