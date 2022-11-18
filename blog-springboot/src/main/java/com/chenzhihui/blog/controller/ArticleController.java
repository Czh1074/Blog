package com.chenzhihui.blog.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.chenzhihui.blog.config.TencentCosConfig;
import com.chenzhihui.blog.dto.*;
import com.chenzhihui.blog.exception.BizException;
import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.pojo.TencentCosPropertiesPicture;
import com.chenzhihui.blog.service.ArticleService;
import com.chenzhihui.blog.vo.ArticleVO;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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
     * 6、点赞文章
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

    /**
     * 8、后台查看文章列表
     *
     * @param conditionVO 查询条件
     * @return {@Link Result<PageResult<ArticleBackDTO>>}
     * */
    @GetMapping("/admin/articles")
    public Result<PageResult<ArticleBackDTO>> listBackArticles(ConditionVO conditionVO){
        return Result.ok(articleService.listBackArticles(conditionVO));
    }

    /**
     * 9、修改文章是否置顶
     *
     * @param articleId,isTop 修改置顶
     * @return {@link Result<?>}
     * */
    @GetMapping("/admin/articles/top")
    public Result<?> updateArticleTop(Integer articleId,Integer isTop){
        articleService.updateArticleTop(articleId, isTop);
        return Result.ok();
    }

    /**
     * 10、后台-根据id查找文章
     *
     * @param articleId 文章信息
     * @return {@link Result<ArticleVO>}
     * */
    @GetMapping("admin/findBackArticleById/{articleId}")
    public Result<ArticleVO> findBackArticleById(@PathVariable("articleId") Integer articleId){
        return Result.ok(articleService.findBackArticleById(articleId));
    }

    /**
     * 11、添加或修改文章
     *
     * @param articleVO 文章信息
     * @return {@link Result<>}
     * */
    @PostMapping("admin/saveOrUpdateArticle2")
    public Result<?> saveOrUpdateArticle(@RequestBody ArticleVO articleVO){
        articleService.saveOrUpdateArticle(articleVO);
        return Result.ok();
    }



    /**
     * 12、上传文章图片
     *
     * @param multipartFile 文件
     * @return {@link Result<String>} 文章图片地址
     */
    @PostMapping("/admin/articles/images")
    @ApiOperation("上传文件到腾讯云的cos中并返回url")
    public Result<String> getUploadFile2TencentCosUrl(@RequestParam("file") MultipartFile multipartFile) throws BizException {
        return Result.ok(articleService.getUploadFileTencentCosUrl(multipartFile));

    }

    /**
     * 13、物理删除文章
     *
     * @param articleId 文章id
     * @return {@link Result<?>}
     * */
    @PostMapping ("/admin/updateArticleDelete/{articleId}")
    public Result<?> updateArticleDelete(@PathVariable("articleId")Integer articleId){
        articleService.updateArticleDelete(articleId);
        return Result.ok();
    }


    /**
     * 14、保存草稿
     *
     * @param articleVO 查询条件
     * @return {@link Result<?>}
     * */
    @PostMapping("/admin/saveDraft")
    public Result<?> saveDraft(ArticleVO articleVO){
        articleService.saveDraft(articleVO);
        return Result.ok();
    }


}


