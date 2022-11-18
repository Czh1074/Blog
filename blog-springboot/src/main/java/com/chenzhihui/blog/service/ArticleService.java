package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.*;
import com.chenzhihui.blog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.ArticleVO;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

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
    List<ArticleHomeDTO> listArticles(Long current);

    /**
     * 3、根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 4、根据标签条件查询文章列表
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

    /**
     * 7、添加或修改文章
     *
     * @param articleVO 文章信息
     * @return {@link Result<>}
     * */
    void saveOrUpdateArticle(ArticleVO articleVO);

    /**
     * 8、后台查看文章列表
     *
     * @param conditionVO 查询条件
     * @return {@Link Result<PageResult<ArticleBackDTO>>}
     * */
    PageResult<ArticleBackDTO> listBackArticles(ConditionVO conditionVO);

    /**
     * 9、修改文章是否置顶
     *
     * @param articleId,isTop 修改置顶
     * @return {@link Result<?>}
     * */
    void updateArticleTop(Integer articleId, Integer isTop);

    /**
     * 10、后台-根据id查找文章
     *
     * @param articleId 文章信息
     * @return {@link Result<ArticleVO>}
     * */
    ArticleVO findBackArticleById(Integer articleId);

    /**
     * 11、后台-上传图片
     *
     * @param multipartFile 文章信息
     * @return {@link String}
     * */
    String getUploadFileTencentCosUrl(MultipartFile multipartFile);

    /**
     * 12、后台-物理删除文章
     *
     * @param articleId 文章信息
     * */
    void updateArticleDelete(Integer articleId);

    /**
     * 14、保存草稿
     *
     * @param articleVO 查询条件
     * */
    void saveDraft(ArticleVO articleVO);
}
