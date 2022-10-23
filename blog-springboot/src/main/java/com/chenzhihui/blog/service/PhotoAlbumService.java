package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.PhotoAlbumDTO;
import com.chenzhihui.blog.pojo.PhotoAlbum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 相册 服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface PhotoAlbumService extends IService<PhotoAlbum> {


    /**
     * 获取相册列表
     *
     * @return {@link List<PhotoAlbumDTO>} 相册列表
     * */
    List<PhotoAlbumDTO> listPhotoAlbum();
}
