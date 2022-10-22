package com.chenzhihui.blog.mapper;

import com.chenzhihui.blog.dto.CategoryDTO;
import com.chenzhihui.blog.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryDTO> listCategoryDTO();
}
