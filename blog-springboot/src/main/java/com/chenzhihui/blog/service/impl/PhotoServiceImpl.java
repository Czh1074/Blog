package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Photo;
import com.chenzhihui.blog.mapper.PhotoMapper;
import com.chenzhihui.blog.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
