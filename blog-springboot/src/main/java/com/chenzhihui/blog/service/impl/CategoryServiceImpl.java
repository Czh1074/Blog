package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.mapper.CategoryMapper;
import com.chenzhihui.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
