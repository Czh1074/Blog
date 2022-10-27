package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.*;
import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.service.ArticleService;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  文章信息控制类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
public class ArticleController {

    @Autowired // 将创建好的容器bean取过来，赋予其成员变量的属性
    private ArticleService articleService;

    /**
     * 1、查看文章归档
     *
     * @return {@link Result < ArchiveDTO >} 文章归档列表
     */
    @ApiOperation(value = "查看文章归档")
    @GetMapping("/articles/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }


    /**
     * 2、查看所有文章
     *
     * @return {@link Result< Article >} 所有文章列表
     */
    @ApiOperation(value = "查看所有文章")
    @GetMapping("/articles/articles/{current}")
    public Result<List<ArticleHomeDTO>> listArticles(@PathVariable("current") Long current) {
        return Result.ok(articleService.listArticles(current));
    }


    /**
     * 3、根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link Result<ArticleDTO>} 文章信息
     * */
    @ApiOperation(value = "根据id查看文章")
    @ApiImplicitParam(name = "articleId",value = "文章id",required = true,dataType = "Integer")
    @GetMapping("/articles/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId){
        return Result.ok(articleService.getArticleById(articleId));
    }


    /**
     * 4、根据条件查询文章 -> 主要运用在发现里的标签这个按钮，点击具体的标签进行文章列表的查找
     *
     * @param condition 条件
     * @return {@link Result<ArticlePreviewListDTO>} 文章列表
     */
    @ApiOperation(value = "根据条件查询文章")
    @GetMapping("/articles/findByCondition")
    public Result<ArticlePreviewListDTO> listArticlesByCondition(ConditionVO condition) {
        System.out.println("输出current" + condition.getLimitCurrent());
        return Result.ok(articleService.listArticlesByCondition(condition));
    }

    /**
     * 5、搜索文章
     *
     * @param condition 条件
     * @return {@link Result<ArticleSearchDTO>} 文章列表
     */
    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public Result<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition) {
        return Result.ok(articleService.listArticlesBySearch(condition));
    }

    /**
     * 点赞文章
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "点赞文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @PostMapping("/articles/{articleId}/like")
    public Result<?> saveArticleLike(@PathVariable("articleId") Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.ok();
    }








        //---- 后台方法⬇️ ----//


//    /**
//     * 添加或修改文章
//     *
//     * @param articleVO 文章信息
//     * @return {@link Result<>}
//     * */
//    @GetMapping("/addOrUpdateArticle")
//    public Result<?> saveOrUpdateArticle(ArticleVO articleVO){
//        articleService.saveOrUpdateArticle(articleVO);
//        return Result.ok();
//    }




}


