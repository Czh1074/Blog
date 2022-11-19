package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.CategoryBackDTO;
import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.service.CategoryService;
import com.chenzhihui.blog.vo.CategoryVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  分类控制类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看分类列表
     *
     * @return {@link Result<CategoryDTO>} 分类列表
     */
    @ApiOperation(value = "查看分类列表")
    @GetMapping("/categories/listCategories")
    public Result<PageResult<CategoryDTO>> listCategories() {
        return Result.ok(categoryService.listCategories());
    }

    /**
     * 查看后台分类列表-带条件查询
     *
     * @param categoryVO 分类名模糊查询
     * @return {@link Result<List<CategoryBackDTO>>} 分类列表
     */
    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/categoryBackList")
    public Result<PageResult<CategoryBackDTO>> categoryBackList(CategoryVO categoryVO) {
        return Result.ok(categoryService.categoryBackList(categoryVO));
    }

    /**
     * 保存或修改分类信息
     *
     * @param categoryVO 分类信息（分类名称、分类id）
     * @return {@link Result<?>}
     * */
    @GetMapping("/admin/addOrEditCategory")
    public Result<?> addOrEditCategory(CategoryVO categoryVO){
        categoryService.addOrEditCategory(categoryVO);
        return Result.ok();
    }

    /**
     * 删除分类信息
     *
     * @param categoryId 分类信息（分类名称、分类id）
     * @return {@link Result<Integer>}
     * */
    @GetMapping("/admin/deleteCategory")
    public Result<Integer> deleteCategory(Integer categoryId){
        return Result.ok(categoryService.deleteCategory(categoryId));
    }


}

