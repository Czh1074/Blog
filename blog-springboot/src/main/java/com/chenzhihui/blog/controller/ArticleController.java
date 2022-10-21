package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.ArchiveDTO;
import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.ArticlePreviewListDTO;
import com.chenzhihui.blog.dto.ArticleSearchDTO;
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
 *  前端控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired // 将创建好的容器bean取过来，赋予其成员变量的属性
    private ArticleService articleService;

    /**
     * 1、查看文章归档
     *
     * @return {@link Result < ArchiveDTO >} 文章归档列表
     */
    @ApiOperation(value = "查看文章归档")
    @GetMapping("/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }


    /**
     * 2、查看所有文章
     *
     * @return {@link Result< Article >} 所有文章列表
     */
    @ApiOperation(value = "查看所有文章")
    @GetMapping("/articles")
    public Result<List<Article>> listArticles() {
        return Result.ok(articleService.listArticles());
    }


    /**
     * 3、根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link Result<ArticleDTO>} 文章信息
     * */
    @ApiOperation(value = "根据id查看文章")
    @ApiImplicitParam(name = "articleId",value = "文章id",required = true,dataType = "Integer")
    @GetMapping("/getArticleById/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId){
        return Result.ok(articleService.getArticleById(articleId));
    }

    /**
     * 4、根据条件查询文章 -> 主要运用在发现里的分类和标签这两个按钮，点击具体的类别进行文章列表的查找
     *
     * @param condition 条件
     * @return {@link Result<ArticlePreviewListDTO>} 文章列表
     */
    @ApiOperation(value = "根据条件查询文章")
    @GetMapping("/findByCondition")
    public Result<ArticlePreviewListDTO> listArticlesByCondition(ConditionVO condition) {
        return Result.ok(articleService.listArticlesByCondition(condition));
    }

    /**
     * 5、搜索文章
     *
     * @param condition 条件
     * @return {@link Result<ArticleSearchDTO>} 文章列表
     */
    @ApiOperation(value = "搜索文章")
    @GetMapping("/search")
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
    @PostMapping("/{articleId}/like")
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


