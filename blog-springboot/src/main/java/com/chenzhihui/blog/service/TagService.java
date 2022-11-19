package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.TagBackDTO;
import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
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

    /**
     * 增加或修改标签
     *
     * @param tagVO 前台标签信息
     * @return {@link Result <?>} 分类列表
     */
    void addOrEditTags(TagVO tagVO);

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    Integer deleteTagById(Integer tagId);
}
