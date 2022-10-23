package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenzhihui.blog.dto.FriendLinkDTO;
import com.chenzhihui.blog.pojo.FriendLink;
import com.chenzhihui.blog.mapper.FriendLinkMapper;
import com.chenzhihui.blog.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.vo.Result;
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
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Resource
    private FriendLinkMapper friendLinkMapper;

    /**
     * 查看友情链接列表
     *
     * @return {@link Result <FriendLinkDTO>} 友情链接列表
     * */
    @Override
    public List<FriendLinkDTO> listFriendLinks() {
        List<FriendLink> listFriendLinks =  friendLinkMapper.selectList(null);
        return BeanCopyUtils.copyList(listFriendLinks,FriendLinkDTO.class);
    }
}
