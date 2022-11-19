package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.CategoryBackDTO;
import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.mapper.ArticleMapper;
import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.mapper.CategoryMapper;
import com.chenzhihui.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.vo.CategoryVO;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Resource
    private ArticleMapper articleMapper;

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
    public PageResult<CategoryBackDTO> categoryBackList(CategoryVO categoryVO) {
        categoryVO.setCurrent((categoryVO.getCurrent()-1)*10);
        Page<Article> page = new Page<>(categoryVO.getCurrent(), PageUtils.getSize());
        System.out.println("输出current：" + categoryVO.getCurrent());
        System.out.println("输出keywords： " + categoryVO.getCategoryName());
        List<CategoryBackDTO> categoryBackDTOList = categoryMapper.categoryBackList(categoryVO);
        return new PageResult<>(categoryBackDTOList,categoryBackDTOList.size());
    }

    /**
     * 保存或修改分类标签
     *
     * @param categoryVO 分类信息（分类名称、分类id）
     * @return {@link Result<?>} 正确
     * */
    @Override
    public void addOrEditCategory(CategoryVO categoryVO) {
        // 判断categoryId是否为空
        if(categoryVO.getCategoryId() == null){ // 空 -> 新增
            Category category = new Category();
            category.setCategoryName(categoryVO.getCategoryName());
            category.setCreateTime(new Date());
            categoryMapper.insert(category);
        }else{ // 非空，查找category，再修改
            Category category = categoryMapper.selectById(categoryVO.getCategoryId());
            category.setCategoryName(categoryVO.getCategoryName());
            categoryMapper.updateById(category);
        }
    }

    /**
     * 删除分类信息
     *
     * @param categoryId 分类信息（分类名称、分类id）
     * @return {@link Result<Integer>}
     * */
    @Override
    public Integer deleteCategory(Integer categoryId) {
        // 查找当前文章目录对应的文章个数
        Integer count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getCategoryId,categoryId));
        if(count == 0){
            categoryMapper.deleteById(categoryId);
        }
        return count;
    }


}
