package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.FriendLinkDTO;
import com.chenzhihui.blog.service.FriendLinkService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  友情连接控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
@RequestMapping("/friendLink")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    /**
     * 查看友情链接列表
     *
     * @return {@link Result<FriendLinkDTO>} 友情链接列表
     * */
    @GetMapping("/listFriendLinks")
    public Result<List<FriendLinkDTO>> listFriendLinks(){
        return Result.ok(friendLinkService.listFriendLinks());
    }
}

