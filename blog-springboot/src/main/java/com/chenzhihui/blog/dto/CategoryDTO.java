package com.chenzhihui.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类从方法
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/22 22:22
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    /**
     * id
     */
    private Integer categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类下的文章数量
     */
    private Integer articleCount;

}