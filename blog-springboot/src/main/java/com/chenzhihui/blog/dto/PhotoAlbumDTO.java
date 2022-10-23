package com.chenzhihui.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/23 10:33
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoAlbumDTO {

    /**
     * 主键
     */
    @TableId(value = "album_id", type = IdType.AUTO)
    private Integer albumId;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 相册封面
     */
    private String albumCover;
}
