package com.chenzhihui.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 后台文章列表返回类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/17 10:56
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBackDTO {

    /**
     * id
     */
    private Integer articleId;

    /**
     * 文章封面
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;


    /**
     * 浏览量
     */
    private Integer viewsCount;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;

    /**
     * 文章类型
     */
    private Integer type;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 文章状态
     */
    private Integer status;

}
