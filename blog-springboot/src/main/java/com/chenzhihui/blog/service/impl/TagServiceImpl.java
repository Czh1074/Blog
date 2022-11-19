package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzhihui.blog.dto.TagBackDTO;
import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.mapper.TagMapper;
import com.chenzhihui.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.TagVO;
import io.swagger.models.auth.In;
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
    public PageResult<TagDTO> listTags() {
        List<Tag> tagList = tagMapper.selectList(null);
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList,TagDTO.class);
        Integer count = tagMapper.selectCount(null);
        return new PageResult<>(tagDTOList,count);
    }

    @Override
    public PageResult<TagBackDTO> tagBackList(TagVO tagVO) {
        tagVO.setCurrent((tagVO.getCurrent()-1)*10);
        Page<Tag> tagPage = new Page<>(tagVO.getCurrent(),tagVO.getSize());
        List<TagBackDTO> tagBackDTOList = tagMapper.tagBackList(tagVO);
        return new PageResult<>(tagBackDTOList,tagBackDTOList.size());
    }
}
