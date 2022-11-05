package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.TalkDTO;
import com.chenzhihui.blog.pojo.Talk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface TalkService extends IService<Talk> {

    /**
     * 查看首页说说: 注：首页部分的说说轮播图
     *
     * @return {@link Result <String>}
     */
    List<String> listHomeTalks();

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkDTO>}
     */
    PageResult<TalkDTO> listTalks(Long current, Long size);

    /**
     * 根据id查看说说详细信息
     *
     * @param talkId
     * @return {@link TalkDTO}
     */
    TalkDTO getTalkById(Integer talkId);
}
