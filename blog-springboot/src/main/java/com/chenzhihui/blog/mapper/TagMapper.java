package com.chenzhihui.blog.mapper;

import com.chenzhihui.blog.dto.ArticlePreviewDTO;
import com.chenzhihui.blog.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenzhihui.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 通过articleId来查找对应的标签列表
     *
     * @param articleId
     *
     * @return {@link List<Tag>}
     * */
    List<Tag> getTagListById(Integer articleId);

}
