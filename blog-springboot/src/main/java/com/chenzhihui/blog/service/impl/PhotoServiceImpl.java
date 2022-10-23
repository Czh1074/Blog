package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzhihui.blog.dto.PhotoBackDTO;
import com.chenzhihui.blog.dto.PhotoDTO;
import com.chenzhihui.blog.pojo.Photo;
import com.chenzhihui.blog.mapper.PhotoMapper;
import com.chenzhihui.blog.pojo.PhotoAlbum;
import com.chenzhihui.blog.service.PhotoAlbumService;
import com.chenzhihui.blog.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.PageResult;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.chenzhihui.blog.constant.CommonConst.FALSE;
import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * <p>
 * 照片 服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private PhotoAlbumService photoAlbumService;

    /**
     * 根据相册id查看照片列表
     *
     * @param albumId 相册id
     * @return {@link List<PhotoDTO>} 照片列表
     */
    @Override
    public PhotoDTO listPhotosByAlbumId(Integer albumId){
        // 查询相册信息
        PhotoAlbum photoAlbum = photoAlbumService.getOne(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getAlbumId,albumId)
                .eq(PhotoAlbum::getIsDelete,FALSE)
                .eq(PhotoAlbum::getStatus,PUBLIC.getStatus()));
        if(Objects.isNull(photoAlbum)){
            System.out.println("相册不存在");
        }
        // 查询照片列表
        // 分页预处理
        // todo：理解getRecords后的方法
        Page<Photo> page = new Page<>(PageUtils.getCurrent(),PageUtils.getSize());
        List<String> photoList = photoMapper.selectPage(page,new LambdaQueryWrapper<Photo>()
                .select(Photo::getPhotoSrc)
                .eq(Photo::getAlbumId,albumId)
                .eq(Photo::getIsDelete,FALSE)
                .orderByDesc(Photo::getPhotoId))
                    .getRecords()
                    .stream() // 转换成流的形式
                    .map(Photo::getPhotoSrc) // 对当前对某个属性进行处理
                    .collect(Collectors.toList()); // 将当前集合转换为list
        return PhotoDTO.builder()
                .photoAlbumCover(photoAlbum.getAlbumCover())
                .photoAlbumName(photoAlbum.getAlbumName())
                .photoList(photoList)
                .build();
    }
}
