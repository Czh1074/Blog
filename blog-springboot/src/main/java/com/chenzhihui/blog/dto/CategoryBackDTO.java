package com.chenzhihui.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 后台分类列表返回类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/17 16:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBackDTO {

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date createTime;

}
