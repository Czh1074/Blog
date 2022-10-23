package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.mapper.TagMapper;
import com.chenzhihui.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagDTO> listTags() {
        List<Tag> tagList = tagMapper.selectList(null);
        return BeanCopyUtils.copyList(tagList,TagDTO.class);
    }
}
