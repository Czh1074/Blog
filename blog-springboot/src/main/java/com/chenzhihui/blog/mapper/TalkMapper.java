package com.chenzhihui.blog.mapper;

import com.chenzhihui.blog.dto.TalkDTO;
import com.chenzhihui.blog.pojo.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface TalkMapper extends BaseMapper<Talk> {

    List<TalkDTO> listTalks(Long limitCurrent, Long size);

    TalkDTO getTalkById(Integer talkId);
}
