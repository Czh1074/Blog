package com.chenzhihui.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友情链接
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/23 09:47
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkDTO {

    /**
     * id
     */
    private Integer linkId;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 介绍
     */
    private String linkIntro;

}
