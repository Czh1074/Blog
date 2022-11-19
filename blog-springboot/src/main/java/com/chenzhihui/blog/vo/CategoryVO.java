package com.chenzhihui.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 目录查找传输类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/18 22:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    /**
     * 页码
     */
    @ApiModelProperty(name = "current", value = "页码", dataType = "Long")
    private Long current;

    /**
     * 条数
     */
    @ApiModelProperty(name = "size", value = "条数", dataType = "Long")
    private Long size;

    /**
     * 分类名
     */
    @ApiModelProperty(name = "categoryName", value = "文章分类", dataType = "String")
    private String categoryName;


    /**
     * 分类Id
     */
    @ApiModelProperty(name = "categoryId", value = "文章分类", dataType = "Integer")
    private Integer categoryId;
}
