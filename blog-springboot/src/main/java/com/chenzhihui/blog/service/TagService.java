package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.TagBackDTO;
import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.TagVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface TagService extends IService<Tag> {

    PageResult<TagDTO> listTags();

    PageResult<TagBackDTO> tagBackList(TagVO tagVO);
}
