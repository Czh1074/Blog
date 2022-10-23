package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.FriendLinkDTO;
import com.chenzhihui.blog.pojo.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * 查看友情链接列表
     *
     * @return {@link Result <FriendLinkDTO>} 友情链接列表
     * */
    List<FriendLinkDTO> listFriendLinks();
}
