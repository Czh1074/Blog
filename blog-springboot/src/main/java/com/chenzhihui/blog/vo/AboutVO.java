package com.chenzhihui.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/22 21:19
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AboutVO {
    /**
     * 关于我的内容
     * */
    private String aboutContent;
}
