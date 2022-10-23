package com.chenzhihui.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 相册
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/23 10:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

    /**
     * 相册封面
     */
    private String photoAlbumCover;

    /**
     * 相册名
     */
    private String photoAlbumName;

    /**
     * 照片列表
     */
    private List<String> photoList;

}
