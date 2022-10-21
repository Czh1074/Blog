package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.mapper.TagMapper;
import com.chenzhihui.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
