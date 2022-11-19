package com.chenzhihui.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签前端输入类
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/19 11:09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {
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
    @ApiModelProperty(name = "tagName", value = "文章标签", dataType = "String")
    private String tagName;


    /**
     * 分类Id
     */
    @ApiModelProperty(name = "tagId", value = "文章标签Id", dataType = "Integer")
    private Integer tagId;
}
