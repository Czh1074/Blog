package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenzhihui.blog.dto.PhotoAlbumDTO;
import com.chenzhihui.blog.pojo.PhotoAlbum;
import com.chenzhihui.blog.mapper.PhotoAlbumMapper;
import com.chenzhihui.blog.service.PhotoAlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.chenzhihui.blog.constant.CommonConst.FALSE;
import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * <p>
 * 相册 服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumMapper, PhotoAlbum> implements PhotoAlbumService {


    @Resource
    private PhotoAlbumMapper photoAlbumMapper;

    /**
     * 获取相册列表
     *
     * @return {@link List<PhotoAlbumDTO>} 相册列表
     * */
    @Override
    public List<PhotoAlbumDTO> listPhotoAlbum() {
        List<PhotoAlbum> listPhotoAlbum = photoAlbumMapper.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getIsDelete,FALSE)
                .eq(PhotoAlbum::getStatus,PUBLIC.getStatus()));
        return BeanCopyUtils.copyList(listPhotoAlbum,PhotoAlbumDTO.class);
    }
}
