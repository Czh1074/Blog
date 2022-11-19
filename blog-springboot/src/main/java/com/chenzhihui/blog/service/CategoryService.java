package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.CategoryBackDTO;
import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.CategoryVO;
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
public interface CategoryService extends IService<Category> {

    /**
     * 查看分类列表
     *
     * @return {@link Result<CategoryDTO>} 分类列表
     */
    PageResult<CategoryDTO> listCategories();

    /**
     * 查看后台分类列表-带条件查询
     *
     * @return {@link Result<PageResult<CategoryBackDTO>>} 分类列表
     */
    PageResult<CategoryBackDTO> categoryBackList(CategoryVO categoryVO);

    /**
     * 保存或修改分类标签
     *
     * @param categoryVO 分类信息（分类名称、分类id）
     * @return {@link Result<?>}
     * */
    void addOrEditCategory(CategoryVO categoryVO);

    /**
     * 删除分类信息
     *
     * @param categoryId 分类信息（分类名称、分类id）
     * @return {@link Result<Integer>}
     * */
    Integer deleteCategory(Integer categoryId);
}
