package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.CategoryBackDTO;
import com.chenzhihui.blog.dto.TagBackDTO;
import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.service.TagService;
import com.chenzhihui.blog.vo.CategoryVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.TagVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
//@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 查看标签列表
     *
     * @return {@link TagDTO} 标签列表
     * */
    @GetMapping("/tags/listTags")
    public Result<PageResult<TagDTO>> listTags(){
        return Result.ok(tagService.listTags());
    }

    /**
     * 查看后台标签列表-带条件查询
     * todo ： 分页没做好
     *
     * @param tagVO 标签名模糊查询
     * @return {@link Result<List< CategoryBackDTO >>} 分类列表
     */
    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/tagBackList")
    public Result<PageResult<TagBackDTO>> categoryBackList(TagVO tagVO) {
        return Result.ok(tagService.tagBackList(tagVO));
    }

    /**
     * 增加或修改标签
     *
     * @param tagVO 前台标签信息
     * @return {@link Result<?>} 分类列表
     */
    @GetMapping("/admin/addOrEditTags")
    public Result<?> addOrEditTags(TagVO tagVO){
        System.out.println("测试标签修改：" + tagVO.getTagName());
        tagService.addOrEditTags(tagVO);
        return Result.ok();
    }

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    @DeleteMapping("/admin/deleteTagById")
    public Result<Integer> deleteTagById(Integer tagId){
        return Result.ok(tagService.deleteTagById(tagId));
    }

}

