package com.chenzhihui.blog.mapper;

import com.chenzhihui.blog.dto.ArticleDTO;
import com.chenzhihui.blog.dto.CategoryBackDTO;
import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenzhihui.blog.vo.CategoryVO;
import com.chenzhihui.blog.vo.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryDTO> listCategoryDTO();

    List<CategoryBackDTO> categoryBackList(@Param("categoryVO") CategoryVO categoryVO);
}
