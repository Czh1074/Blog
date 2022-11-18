package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.CategoryBackDTO;
import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.mapper.CategoryMapper;
import com.chenzhihui.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查看分类列表
     *
     * @return {@link Result<CategoryDTO>} 分类列表
     */
    @Override
    public PageResult<CategoryDTO> listCategories() {
        // 查找文章类别列表、列表id、名称
        // 通过category获得category_i，然后通过category_id来查找article列表中有对应id到个数
        return new PageResult<>(categoryMapper.listCategoryDTO(),categoryMapper.selectCount(null));
    }

    /**
     * 查看后台分类列表
     *
     * @return {@link Result<CategoryBackDTO>} 后台分类列表
     */
    @Override
    public PageResult<CategoryBackDTO> categoryBackList() {
        //
        return null;
    }

}
