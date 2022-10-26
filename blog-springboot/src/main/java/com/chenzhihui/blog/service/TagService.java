package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.PageResult;

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
}
