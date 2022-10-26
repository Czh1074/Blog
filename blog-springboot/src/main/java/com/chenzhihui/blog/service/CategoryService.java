package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;

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

}
