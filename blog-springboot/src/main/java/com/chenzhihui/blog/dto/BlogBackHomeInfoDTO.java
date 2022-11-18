package com.chenzhihui.blog.dto;

import com.chenzhihui.blog.pojo.Article;
import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.vo.PageVO;
import com.chenzhihui.blog.vo.WebsiteConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 后台博客信息返回类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/16 23:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogBackHomeInfoDTO {

    /**
     * 访问数量
     */
    private String viewCount;

    /**
     * 用户数量
     */
    private Integer userCount;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 高热度文章列表
     */
    private List<ArticleDTO> articleList;

    /**
     * 标签列表
     */
    private List<TagDTO> tagDTOList;
}
