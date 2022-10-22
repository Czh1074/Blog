package com.chenzhihui.blog.dto;

import com.chenzhihui.blog.vo.PageVO;
import com.chenzhihui.blog.vo.WebsiteConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 博客首页信息
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/22 19:27
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoDTO {

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

    /**
     * 访问量
     */
    private String viewsCount;

    /**
     * 网站配置
     */
    private WebsiteConfigVO websiteConfig;

    /**
     * 页面列表
     */
    private List<PageVO> pageList;

}
