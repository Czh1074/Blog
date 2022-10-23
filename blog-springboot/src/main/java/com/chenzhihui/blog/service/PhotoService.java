package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.PhotoDTO;
import com.chenzhihui.blog.pojo.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.ConditionVO;

/**
 * <p>
 * 照片 服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface PhotoService extends IService<Photo> {

    /**
     * 根据相册id查看照片列表
     *
     * @param albumId 相册id
     * @return {@link List<PhotoDTO>} 照片列表
     */
    PhotoDTO listPhotosByAlbumId(Integer albumId);
}
