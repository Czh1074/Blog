package com.chenzhihui.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 后台文章标签返回类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/19 11:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagBackDTO {
    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 标签名称
     */
    private String tagName;

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
